package org.producer.consumer.quiz.Controller;


import jakarta.servlet.http.HttpServletRequest;
import org.producer.consumer.quiz.Response.ApiResponse;
import org.producer.consumer.quiz.Services.Interfaces.ResultServiceInterface;
import org.producer.consumer.quiz.Services.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/result")
public class ResultController {

    private final ResultServiceInterface resultService;
    private final JwtService jwtService;
    public ResultController(ResultServiceInterface resultService,JwtService jwtService) {
        this.resultService = resultService;
        this.jwtService = jwtService;
    }

    @GetMapping
    public ResponseEntity<?> viewResults(HttpServletRequest request) throws IOException {

            String token = request.getHeader("Authorization");

            if (token == null || !token.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse(HttpStatus.BAD_REQUEST.value(), "Missing or invalid Authorization header."));
            }
            token = token.substring(7);
            Long userId = Long.valueOf(jwtService.extractUsername(token));
            return resultService.viewResults(userId);
    }
}
