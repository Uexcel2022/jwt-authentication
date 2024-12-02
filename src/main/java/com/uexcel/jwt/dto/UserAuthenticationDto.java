package com.uexcel.jwt.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class UserAuthenticationDto {
    @NotEmpty(message = "first name is required")
    @NotNull(message = "first name is required")
    private String firstName;
    @NotEmpty(message = "Last name is required")
    @NotNull(message = "last name is required")
    private String lastName;
    private String email;
    @NotEmpty(message = "Password name is required")
    @NotNull(message = "Password name is required")
    private String password;
    @NotEmpty(message = "Confirm password name is required")
    @NotNull(message = "Confirm password name is required")
    private String confirmPassword;
}
