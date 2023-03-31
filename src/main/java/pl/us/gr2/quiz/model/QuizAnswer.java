package pl.us.gr2.quiz.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizAnswer {
    @Min(1L)
    private long quizId;
    @NotEmpty
    private String answer;
    @Min(1L)
    private long userId;
}
