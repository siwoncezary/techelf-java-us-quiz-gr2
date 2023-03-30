package pl.us.gr2.quiz.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {
    private String error;

    private String question;

    private String userAnswer;

    private boolean isCorrect;
}
