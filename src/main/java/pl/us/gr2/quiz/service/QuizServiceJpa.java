package pl.us.gr2.quiz.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import pl.us.gr2.quiz.model.Feedback;
import pl.us.gr2.quiz.model.Quiz;
import pl.us.gr2.quiz.model.QuizAnswer;
import pl.us.gr2.quiz.repository.QuizRepositoryJpa;
import pl.us.gr2.quiz.repository.UserRepositoryJap;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("QuizServiceJpa")
@Primary
public class QuizServiceJpa implements QuizService{
    private final QuizRepositoryJpa quizRepository;
    private final UserRepositoryJap userRepository;
    private final List<QuizAnswer> quizAnswers = new ArrayList<>();

    public QuizServiceJpa(QuizRepositoryJpa quizRepositoryJpa, UserRepositoryJap userRepositoryJap) {
        this.quizRepository = quizRepositoryJpa;
        this.userRepository = userRepositoryJap;
    }

    @Override
    public List<Quiz> findAllQuizzes() {
        return quizRepository.findAll();
    }

    @Override
    public Optional<Quiz> findQuizById(long id) {
        return quizRepository.findById(id);
    }

    @Override
    public Feedback saveAnswerForQuiz(QuizAnswer quizAnswer) {
        final Optional<Quiz> optionalQuiz = quizRepository.findById(quizAnswer.getQuizId());
        if (optionalQuiz.isEmpty()){
            return Feedback
                    .builder()
                    .error("Quiz not found. Answer not saved")
                    .build();
        }
        final Quiz quiz = optionalQuiz.get();
        quizAnswers.add(quizAnswer);
        return Feedback
                .builder()
                .question(quiz.getQuestion())
                .userAnswer(quizAnswer.getAnswer())
                .isCorrect(quiz.isCorrectAnswer(quizAnswer.getAnswer()))
                .build();
    }

    @Override
    public long countCorrectAnswersForQuiz(long quizId) {
        final Optional<Quiz> optionalQuiz = quizRepository.findById(quizId);
        if (optionalQuiz.isEmpty()) {
            return 0;
        }
        var quiz = optionalQuiz.get();
        // wersja klasyczna
        long counter = 0;
        for(var q: quizAnswers){
            if (q.getQuizId() == quizId && quiz.isCorrectAnswer(q.getAnswer())){
                counter++;
            }
        }
        return counter;
        // wersja ze stream
//        return quizAnswers
//                .stream()
//                .filter(q -> q.getQuizId() == quizId)
//                .filter(q -> quiz.isCorrectAnswer(q.getAnswer()))
//                .count();
    }
}
