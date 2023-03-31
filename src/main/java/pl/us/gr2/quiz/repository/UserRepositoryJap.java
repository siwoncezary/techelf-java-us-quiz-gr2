package pl.us.gr2.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.us.gr2.quiz.model.User;

public interface UserRepositoryJap extends JpaRepository<User, Long> {
}
