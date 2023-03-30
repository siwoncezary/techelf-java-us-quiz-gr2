package pl.us.gr2.quiz.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.us.gr2.quiz.model.Quiz;
import pl.us.gr2.quiz.repository.QuizRepository;

import java.util.List;

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
}
