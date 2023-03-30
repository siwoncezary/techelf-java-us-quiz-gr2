package pl.us.gr2.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.us.gr2.quiz.model.Quiz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseQuizDto {
    public long id;
    public String question;
    public List<String> options;
    public String title;

    public static ResponseQuizDto of(Quiz quiz){
        List<String> options = new ArrayList<>(quiz.getIncorrectAnswers());
        options.add(quiz.getCorrectAnswer());
        Collections.shuffle(options);
        return ResponseQuizDto
                .builder()
                .id(quiz.getId())
                .question(quiz.getQuestion())
                .title(quiz.getTitle())
                .options(options)
                .build();
    }
}
