package pl.us.gr2.quiz.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Quiz implements Serializable {

    private long id;

    private String title;

    private String question;

    private List<String> incorrectAnswers;

    private String correctAnswer;

    public boolean isCorrectAnswer(String answer){
        return Objects.equals(answer, correctAnswer);
    }
}
