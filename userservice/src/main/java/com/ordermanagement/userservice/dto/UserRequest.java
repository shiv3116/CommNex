package com.ordermanagement.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequest {

    @NotBlank(message = "username cannot be empty")
    String username;

    @NotBlank(message = "email is required")
    @Email(message = "invalid email")
    String email;

    @NotBlank(message = "password cannot be empty")
    @Size(min = 8, message = "password should be 8 characters long")
    @Size(max = 32, message = "password cannot be more than 32 characters long")
    String password;
}
