package pl.us.gr2.quiz.repository;

import org.springframework.stereotype.Component;
import pl.us.gr2.quiz.model.Quiz;

import java.util.*;

@Component
public class QuizRepository {
    private Map<Long, Quiz> quizzes = new HashMap<>();

    public QuizRepository() {
        final Quiz quiz = Quiz.builder()
                .title("Dodawanie")
                .question("2 + 3 = ?")
                .correctAnswer("5")
                .incorrectAnswersAsString("4|6|7")
                .build();
        save(quiz);
        save(
                Quiz.builder()
                        .title("Odejmowanie")
                        .question("7 - 3 = ?")
                        .correctAnswer("4")
                        .incorrectAnswersAsString("5|6|7")
                        .build()
        );
    }

    private long index = 0;

    public List<Quiz> findAll() {
        return quizzes.values().stream().toList();
    }

    public Optional<Quiz> findById(long id) {
        final Quiz quiz = quizzes.get(id);
        return quiz == null ? Optional.empty() : Optional.of(quiz);
    }

    public Quiz save(Quiz quiz) {
        quiz.setId(++index);
        quizzes.put(quiz.getId(), quiz);
        return quiz;
    }

    public void removeById(long id) {
        if (quizzes.containsKey(id)) {
            quizzes.remove(id);
        } else {
            throw new IllegalArgumentException("Key not found");
        }
    }

    public Optional<Quiz> update(Quiz update){
        if (quizzes.containsKey(update.getId())) {
            quizzes.put(update.getId(), update);
            return Optional.of(update);
        } else {
            return Optional.empty();
        }
    }
}
