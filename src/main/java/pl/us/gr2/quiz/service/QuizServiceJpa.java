package pl.us.gr2.quiz.service;

import pl.us.gr2.quiz.model.Feedback;
import pl.us.gr2.quiz.model.Quiz;
import pl.us.gr2.quiz.model.QuizAnswer;

import java.util.List;
import java.util.Optional;

public class QuizServiceJpa implements QuizService{
    @Override
    public List<Quiz> findAllQuizzes() {
        return null;
    }

    @Override
    public Optional<Quiz> findQuizById(long id) {
        return Optional.empty();
    }

    @Override
    public Feedback saveAnswerForQuiz(QuizAnswer quizAnswer) {
        return null;
    }

    @Override
    public long countCorrectAnswersForQuiz(long quizId) {
        return 0;
    }
}
