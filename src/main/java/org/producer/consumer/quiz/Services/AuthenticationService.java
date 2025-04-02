package org.producer.consumer.quiz.Services;


import org.producer.consumer.quiz.Exception.ResourceNotFoundException;
import org.producer.consumer.quiz.Model.Department;
import org.producer.consumer.quiz.Model.Role;
import org.producer.consumer.quiz.Model.User;
import org.producer.consumer.quiz.Repositories.DepartmentRepository;
import org.producer.consumer.quiz.Repositories.TokenRepository;
import org.producer.consumer.quiz.Repositories.UserRepository;
import org.producer.consumer.quiz.Request.AuthenticationRequest;
import org.producer.consumer.quiz.Request.RegistrationRequest;
import org.producer.consumer.quiz.Request.UserRegistrationRequest;
import org.producer.consumer.quiz.Response.ApiResponse;
import org.producer.consumer.quiz.Response.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    private final DepartmentRepository departmentRepository;


    public ResponseEntity<ApiResponse> registerUser(UserRegistrationRequest request) {
        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enabled(true)
                .role(Role.USER)
                .department(department)
                .build();

        userRepository.save(user);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(),"User registered successfully!"));
    }

    public ResponseEntity<ApiResponse> registerAdmin(RegistrationRequest request) {
        var admin = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enabled(true)
                .role(Role.ADMIN)
                .build();

        userRepository.save(admin);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(),"Admin registered successfully!" ));
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var claims = new HashMap<String, Object>();
        var user = ((User) auth.getPrincipal());
        claims.put("sub", user.getId());

        var jwtToken = jwtService.generateToken(claims, (User) auth.getPrincipal());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .flag((user.getRole().equals(Role.ADMIN) ? (byte) 1 : (byte) 2))
                .build();
    }
}