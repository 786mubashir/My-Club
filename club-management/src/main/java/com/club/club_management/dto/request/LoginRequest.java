package com.club.club_management.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    @NotBlank
    @Email(message = "Invalid email")
    private String email;

    @NotBlank
    private String password;

   
}
