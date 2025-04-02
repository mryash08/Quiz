package org.producer.consumer.quiz.Request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QuestionDto {

        @NotNull(message = "Department ID cannot be null")
        private Long departmentId;
        @NotEmpty(message = "Question cannot be null")
        private String question;
        @NotEmpty(message = "Option cannot be null")
        private String optionA;
        @NotEmpty(message = "Option cannot be null")
        private String optionB;
        @NotEmpty(message = "Option cannot be null")
        private String optionC;
        @NotEmpty(message = "Option cannot be null")
        private String optionD;
        @NotEmpty(message = "Correct Option cannot be null")
        private String correctOption;
    }

