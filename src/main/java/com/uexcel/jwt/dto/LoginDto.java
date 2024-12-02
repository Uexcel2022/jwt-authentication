package com.uexcel.jwt.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginDto {
    @NotEmpty(message = "Please enter email.")
    @NotNull(message = "Please enter email.")
    private String email;
    @NotEmpty(message = "Please enter password.")
    @NotNull(message = "Please enter password.")
    private String password;
}
