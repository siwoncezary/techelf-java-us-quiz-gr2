package pl.us.gr2.quiz.service;
import pl.us.gr2.quiz.model.Feedback;
import pl.us.gr2.quiz.model.Quiz;
import pl.us.gr2.quiz.model.QuizAnswer;

import java.util.*;
public interface QuizService {
    List<Quiz> findAllQuizzes();

    Optional<Quiz> findQuizById(long id);

    Feedback saveAnswerForQuiz(QuizAnswer quizAnswer);

    long countCorrectAnswersForQuiz(long quizId);
}
