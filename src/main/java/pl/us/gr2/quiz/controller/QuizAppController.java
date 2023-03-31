package pl.us.gr2.quiz.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.us.gr2.quiz.dto.ResponseQuizDto;
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
}
