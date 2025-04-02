package org.producer.consumer.quiz.Controller;


import jakarta.validation.Valid;
import org.producer.consumer.quiz.Request.QuestionDto;
import org.producer.consumer.quiz.Exception.ResourceNotFoundException;
import org.producer.consumer.quiz.Response.ApiResponse;
import org.producer.consumer.quiz.Services.Interfaces.QuestionServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/questions")
@PreAuthorize("hasAuthority('ADMIN')")
@Validated
public class QuestionController {

    @Autowired
    private QuestionServiceInterface questionService;

    @PostMapping
    public ResponseEntity<ApiResponse> addQuestion(@Valid @RequestBody QuestionDto questionDto) {
        ApiResponse response;
        try {
            return questionService.addQuestion(questionDto);
        } catch (DataIntegrityViolationException e) {
            response = new ApiResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    "Invalid data: " + e.getMessage()
            );
            return ResponseEntity.ok(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateQuestion(@PathVariable Long id, @RequestBody @Valid QuestionDto questionDto) {
        try {
            return questionService.updateQuestion(id, questionDto);
        }
        catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(HttpStatus.BAD_REQUEST.value(),"Invalid data: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteQuestion(@PathVariable Long id) {
        try {
            return questionService.deleteQuestion(id);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(HttpStatus.NOT_FOUND.value(), "Not Found" + e.getMessage()));
        }
    }


}
