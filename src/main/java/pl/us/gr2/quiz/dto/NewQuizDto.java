package pl.us.gr2.quiz.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class NewQuizDto implements Serializable {

    @Length(min = 3, max = 50)
    private String title;

    @Length(min = 3, max = 250)
    private String question;

    @Size(min = 1, max = 6)
    private List<String> incorrectAnswers;

    @NotEmpty(message = "Musisz podać odpowiedź!")
    private String correctAnswer;
}
