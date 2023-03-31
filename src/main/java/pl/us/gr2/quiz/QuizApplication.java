package pl.us.gr2.quiz;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.us.gr2.quiz.model.Quiz;
import pl.us.gr2.quiz.repository.QuizRepositoryJpa;

@SpringBootApplication
public class QuizApplication implements CommandLineRunner {
    private final QuizRepositoryJpa quizRepositoryJpa;

    public QuizApplication(QuizRepositoryJpa quizRepositoryJpa) {
        this.quizRepositoryJpa = quizRepositoryJpa;
    }

    public static void main(String[] args) {

        SpringApplication.run(QuizApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (quizRepositoryJpa.count() < 3){
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
    }
}
