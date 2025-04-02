package org.producer.consumer.quiz.Services.Interfaces;

import org.springframework.http.ResponseEntity;

public interface ResultServiceInterface {

    ResponseEntity<?> viewResults(Long userId);
}
