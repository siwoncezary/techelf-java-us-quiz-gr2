package pl.us.gr2.quiz.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import pl.us.gr2.quiz.dto.ResponseQuizDto;
import pl.us.gr2.quiz.model.Feedback;
import pl.us.gr2.quiz.model.QuizAnswer;
import pl.us.gr2.quiz.service.QuizService;
import pl.us.gr2.quiz.service.QuizServiceJpa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v2/quizzes")
public class QuizAppController {
    private final QuizService quizService;

    public QuizAppController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/")
    public List<ResponseQuizDto> getAllQuizzes(){
        return quizService
                .findAllQuizzes()
                .stream()
                .map(ResponseQuizDto::of)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseQuizDto> getQuiz(@PathVariable long id){
        return ResponseEntity.of(quizService.findQuizById(id).map(ResponseQuizDto::of));
    }

    @PostMapping("/{id}/answers")
    public ResponseEntity<Feedback> saveQuizAnswer(@RequestBody @Valid QuizAnswer quizAnswer, @PathVariable long id){
        if (id != quizAnswer.getQuizId()){
            return ResponseEntity.badRequest().build();
        }
        final Feedback feedback = quizService.saveAnswerForQuiz(quizAnswer);
        return ResponseEntity.ok(feedback);
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
}
