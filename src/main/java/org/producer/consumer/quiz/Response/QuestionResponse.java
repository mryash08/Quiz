package org.producer.consumer.quiz.Response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResponse {
    private Long id;
    private Long departmentId;
    private String question;
    private List<String> options;

}
