package pl.us.gr2.quiz.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


@Entity(name = "quizzes")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Quiz implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Getter@Setter
    private long id;

    @Getter@Setter
    @Column(length = 50, nullable = false)
    private String title;

    @Getter@Setter
    private String question;

    // "3|5|8"
    @Column(name = "incorrectAnswers")
    private String incorrectAnswersAsString;

    @Getter@Setter
    private String correctAnswer;

    public boolean isCorrectAnswer(String answer){
        return Objects.equals(answer, correctAnswer);
    }

    public void setIncorrectAnswers(List<String> answers){
        incorrectAnswersAsString = String.join("|", answers);
    }

    public List<String> getIncorrectAnswers(){
        return Arrays.stream(incorrectAnswersAsString.split("\\|}")).toList();
    }
}
