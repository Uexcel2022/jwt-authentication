package com.uexcel.jwt.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
@Schema(name = "Login",description = "This schema will hold login credentials")
@Getter @Setter
public class LoginDto {
    @NotEmpty(message = "Please enter email.")
    @NotNull(message = "Please enter email.")
    private String email;
    @NotEmpty(message = "Please enter password.")
    @NotNull(message = "Please enter password.")
    private String password;
}
