package pl.us.gr2.quiz;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.us.gr2.quiz.model.Quiz;
import pl.us.gr2.quiz.model.User;
import pl.us.gr2.quiz.repository.QuizRepositoryJpa;
import pl.us.gr2.quiz.repository.UserRepositoryJap;

@SpringBootApplication
public class QuizApplication implements CommandLineRunner {
    private final QuizRepositoryJpa quizRepositoryJpa;
    private final UserRepositoryJap userRepositoryJap;

    private final PasswordEncoder encoder;

    public QuizApplication(QuizRepositoryJpa quizRepositoryJpa, UserRepositoryJap userRepositoryJap, PasswordEncoder encoder) {
        this.quizRepositoryJpa = quizRepositoryJpa;
        this.userRepositoryJap = userRepositoryJap;
        this.encoder = encoder;
    }

    public static void main(String[] args) {

        SpringApplication.run(QuizApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (quizRepositoryJpa.count() < 3) {
            quizRepositoryJpa.save(
                    Quiz.builder()
                            .title("Mnożenie")
                            .correctAnswer("6")
                            .question("2 * 3")
                            .incorrectAnswersAsString("3|4|5")
                            .build()
            );
            quizRepositoryJpa.save(
                    Quiz.builder()
                            .title("Dodawanie")
                            .correctAnswer("5")
                            .question("2 + 3")
                            .incorrectAnswersAsString("3|4|6")
                            .build()
            );
            quizRepositoryJpa.save(
                    Quiz.builder()
                            .title("Dzielenie")
                            .correctAnswer("6")
                            .question("12 / 2")
                            .incorrectAnswersAsString("3|4|5")
                            .build()
            );
        }
        if (userRepositoryJap.count() < 2) {
            userRepositoryJap.save(User
                    .builder()
                    .email("karocl@op.pl")
                    .roles("ADMIN")
                    .password(encoder.encode("1234"))
                    .build()
            );
            userRepositoryJap.save(User
                    .builder()
                    .email("ewa@op.pl")
                    .roles("USER")
                    .password(encoder.encode("abcd"))
                    .build());
        }
    }
}
