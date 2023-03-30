package pl.us.gr2.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewQuizDto implements Serializable {

    private String title;

    private String question;

    private List<String> incorrectAnswers;

    private String correctAnswer;
}
