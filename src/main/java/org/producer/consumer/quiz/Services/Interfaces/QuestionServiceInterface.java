package org.producer.consumer.quiz.Services.Interfaces;

import org.producer.consumer.quiz.Request.QuestionDto;
import org.producer.consumer.quiz.Response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface QuestionServiceInterface {
    ResponseEntity<ApiResponse> addQuestion(@RequestBody QuestionDto questionDto);

    ResponseEntity<ApiResponse> updateQuestion(@PathVariable Long id, @RequestBody QuestionDto questionDto) ;

    ResponseEntity<ApiResponse> deleteQuestion(@PathVariable Long id);
}
