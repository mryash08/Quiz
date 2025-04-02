package org.producer.consumer.quiz.Exception;

import org.producer.consumer.quiz.Response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),"An error occurred: " + ex.getMessage() ));
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse> handleTypeMismatch(MethodArgumentTypeMismatchException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(HttpStatus.BAD_REQUEST.value(), "Invalid input type: " + e.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse> handleBadCredentials(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(HttpStatus.UNAUTHORIZED.value(), "Bad Credentials: "+ ex.getMessage()));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiResponse> handleNotFoundException(NoHandlerFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(HttpStatus.NOT_FOUND.value(), "API not found. Please check the URL."));
    }

    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<ApiResponse> handleUnAuthorizedException(UnAuthorizedException ex) {
        System.out.println("UnAuthorizes Invoke");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(HttpStatus.UNAUTHORIZED.value(),"Token is expired"));
    }
}
