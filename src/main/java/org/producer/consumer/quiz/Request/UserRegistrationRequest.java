package org.producer.consumer.quiz.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class UserRegistrationRequest {

    @NotEmpty(message = "email is mandatory")
    @Email(message = "Email is not well formatted")
    private String email;
    @NotEmpty(message = "Password is mandatory")
    @NotNull(message = "Password is mandatory")
    @Size(min = 8, message = "Password should be 8 characters long minimum")
    private String password;
    private String Role = "USER";
    @NotNull(message = "Department ID is mandatory")
    private Long departmentId;
}
