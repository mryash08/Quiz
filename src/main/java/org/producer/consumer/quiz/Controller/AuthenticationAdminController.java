package org.producer.consumer.quiz.Controller;


import org.producer.consumer.quiz.Request.AuthenticationRequest;
import org.producer.consumer.quiz.Request.RegistrationRequest;
import org.producer.consumer.quiz.Response.ApiResponse;
import org.producer.consumer.quiz.Response.AuthenticationResponse;
import org.producer.consumer.quiz.Services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/auth")
@Validated
@RequiredArgsConstructor

public class AuthenticationAdminController {

    private final AuthenticationService service;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<ApiResponse> register(
            @RequestBody @Valid RegistrationRequest request
    ) {
        request.setRole("ADMIN");
        service.registerAdmin(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponse(HttpStatus.ACCEPTED.value(), "Admin Registered Successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@Valid @RequestBody AuthenticationRequest request) {
        try {
            AuthenticationResponse authenticationResponse = service.authenticate(request);
            return ResponseEntity.ok(authenticationResponse);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(HttpStatus.UNAUTHORIZED.value(), "Invalid Credentials"));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error " + ex.getMessage()));
        }
    }
}