package pl.us.gr2.quiz.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import pl.us.gr2.quiz.dto.NewQuizDto;
import pl.us.gr2.quiz.model.Quiz;
import pl.us.gr2.quiz.repository.QuizRepository;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/quizzes")
public class QuizController {
    private final QuizRepository quizRepository;

    public QuizController(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    @GetMapping("")
    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable long id) {
        return ResponseEntity.of(quizRepository.findById(id));
    }

    @PostMapping("")
    public ResponseEntity<Quiz> saveQuiz(@Valid @RequestBody NewQuizDto quiz) {
        var saved = quizRepository.save(
                Quiz.builder()
                        .title(quiz.getTitle())
                        .question(quiz.getQuestion())
                        .incorrectAnswersAsString(String.join("|",quiz.getIncorrectAnswers()))
                        .correctAnswer(quiz.getCorrectAnswer())
                        .build()
        );
        return ResponseEntity.created(URI.create("/api/v1/quizzes/" + saved.getId())).body(saved);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteQuiz(@PathVariable long id){
        try {
            quizRepository.removeById(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Quiz> updateQuiz(@PathVariable long id, @RequestBody Quiz update){
        if (id != update.getId()){
            return ResponseEntity.badRequest().build();
        }
        final Optional<Quiz> quizOptional = quizRepository.update(update);
        if (quizOptional.isPresent()){
            return ResponseEntity.of(quizOptional);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping(value = "/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Quiz> patchQuiz(@PathVariable long id, @RequestBody JsonPatch patched){
        ObjectMapper mapper = new ObjectMapper();
        final Optional<Quiz> optionalQuiz = quizRepository.findById(id);
        if(optionalQuiz.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        Quiz quiz = optionalQuiz.get();
        final var node = mapper.convertValue(quiz, JsonNode.class);
        try {
            final JsonNode applied = patched.apply(node);
            final Quiz patchedQuiz = mapper.treeToValue(applied, Quiz.class);
            final Optional<Quiz> quizOptional = quizRepository.update(patchedQuiz);
            return ResponseEntity.of(quizOptional);
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Test failed, patch not applied!")).build();
        }
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleException(MethodArgumentNotValidException ex) {
        Map<String, Object> errors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            String defaultMessage = fieldError.getDefaultMessage();
            errors.put(fieldError.getField(), defaultMessage);
        }
        return errors;
    }

    @ExceptionHandler(JsonPatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleJsonPatchException(JsonPatchException ex) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("patch error", ex.getMessage());
        return errors;
    }

}
