package pl.us.gr2.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.us.gr2.quiz.model.Quiz;

@Repository
public interface QuizRepositoryJpa extends JpaRepository<Quiz, Long> {
}
