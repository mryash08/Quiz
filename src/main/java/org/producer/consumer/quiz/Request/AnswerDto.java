package org.producer.consumer.quiz.Request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class AnswerDto {
    @NotNull(message = "User ID cannot be null")
    private Long userId;
    @NotEmpty(message = "Answers cannot be empty")
    private List<AnswerItem> answers;

    @Data
    public static class AnswerItem {
        private Long questionId;
        private String selectedOption;
    }
}
