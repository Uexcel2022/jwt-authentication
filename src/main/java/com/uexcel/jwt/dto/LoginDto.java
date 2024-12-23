package com.uexcel.jwt.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Schema(name = "Login",description = "This schema will hold login credentials")
@Getter @Setter
public class LoginDto {
    @NotEmpty(message = "Please enter email.")
    @NotNull(message = "Please enter email.")
    @Email(message = "Please enter a valid email.")
    private String email;
    @NotEmpty(message = "Please enter password.")
    @NotNull(message = "Please enter password.")
    @Length(min = 8, max = 16, message = "Password must be 8 - 16 characters long.")
    private String password;
}
