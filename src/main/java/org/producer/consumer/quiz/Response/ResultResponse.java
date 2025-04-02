package org.producer.consumer.quiz.Response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultResponse {

    private String message;
    private String result;
    private Double percentage;


}
