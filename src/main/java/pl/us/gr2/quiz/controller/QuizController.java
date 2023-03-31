package pl.us.gr2.quiz.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
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
import java.util.stream.Collectors;

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

    //Dodaj metodę zwracająca quiz o podanym id w ścieżce
    //przykład /api/v1/quizzes/1 to zostanie wysłany quiz o id = 1

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
    public ResponseEntity deleteQuiz(@PathVariable long id){
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleException(MethodArgumentNotValidException e){
        Map<String, Object> errors = new HashMap<>();
        for(var error: e.getDetailMessageArguments()){
            if (error.toString().contains(":")) {
                final String[] split = error.toString().split(":");
                errors.put(split[0], split[1]);
            }
        }
        return errors;
    }
}
