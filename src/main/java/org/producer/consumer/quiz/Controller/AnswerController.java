package org.producer.consumer.quiz.Controller;



import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.producer.consumer.quiz.Request.AnswerDto;
import org.producer.consumer.quiz.Exception.InvalidInputException;
import org.producer.consumer.quiz.Exception.ResourceNotFoundException;
import org.producer.consumer.quiz.Response.ApiResponse;
import org.producer.consumer.quiz.Services.Interfaces.AnswerServiceInterface;
import org.producer.consumer.quiz.Services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/answer")
public class AnswerController {

    private final AnswerServiceInterface answerServiceInterface;
    private final JwtService jwtService;

    @Autowired
    public AnswerController(AnswerServiceInterface answerServiceInterface, JwtService jwtService) {
        this.answerServiceInterface = answerServiceInterface;
        this.jwtService = jwtService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> submitAnswers(@Valid  @RequestBody AnswerDto answerDto) {
        if (answerDto == null || answerDto.getAnswers() == null || answerDto.getAnswers().isEmpty()) {
            throw new InvalidInputException("Answer submission cannot be empty.");
        }

        if (answerDto.getUserId() <= 0) {
            throw new InvalidInputException("Invalid user ID.");
        }

        boolean userExists = answerServiceInterface.isUserExists(answerDto.getUserId());
        if (!userExists) {
            throw new ResourceNotFoundException("User with ID " + answerDto.getUserId() + " not found.");
        }

        String result = answerServiceInterface.submitAnswers(answerDto);

        ApiResponse response = new ApiResponse(
                HttpStatus.OK.value(),
                result
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/reset")
    public ResponseEntity<ApiResponse> resetAnswers(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(HttpStatus.BAD_REQUEST.value(), "Missing or invalid Authorization header."));
        }

        token = token.substring(7);
        Long userId = Long.valueOf(jwtService.extractUsername(token));
        answerServiceInterface.resetAnswers(userId);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), "Answers reset successfully."));
    }
}
