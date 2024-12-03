package com.uexcel.jwt.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Schema(name = "UserAuthentication",description = "This schema will hold user info for sign up.")
@Getter @Setter @ToString
public class UserAuthenticationDto extends EmailDto {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotEmpty(message = "Password is required.")
    @NotNull(message = "Password is required.")
    private String password;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotEmpty(message = "Confirm password is required.")
    @NotNull(message = "Confirm password is required.")
    private String confirmPassword;

}
