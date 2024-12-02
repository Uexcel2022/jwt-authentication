package com.uexcel.jwt.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class UserAuthenticationDto {
    @NotEmpty(message = "First name is required.")
    @NotNull(message = "First name is required.")
    private String firstName;
    @NotEmpty(message = "Last name is required.")
    @NotNull(message = "Last name is required.")
    private String lastName;
    @NotEmpty(message = "email is required.")
    @NotNull(message = "email is required.")
    @Email(message = "please a valid email address")
    private String email;
    @NotEmpty(message = "Password is required.")
    @NotNull(message = "Password is required.")
    private String password;
    @NotEmpty(message = "Confirm password is required.")
    @NotNull(message = "Confirm password is required.")
    private String confirmPassword;
}
