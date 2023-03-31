package pl.us.gr2.quiz.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.us.gr2.quiz.dto.ResponseQuizDto;
import pl.us.gr2.quiz.model.Feedback;
import pl.us.gr2.quiz.model.QuizAnswer;
import pl.us.gr2.quiz.service.QuizService;
import pl.us.gr2.quiz.service.QuizServiceJpa;

import java.util.List;

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
    public ResponseEntity<Feedback> saveQuizAnswer(@RequestBody QuizAnswer quizAnswer, @PathVariable long id){
        if (id != quizAnswer.getQuizId()){
            return ResponseEntity.badRequest().build();
        }
        final Feedback feedback = quizService.saveAnswerForQuiz(quizAnswer);
        return ResponseEntity.ok(feedback);
    }
}
