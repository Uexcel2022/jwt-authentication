package com.uexcel.jwt.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
@Schema(name = "Email",description = "This schema will hold user email address in addition to other user info.")
@Getter @Setter
public class EmailDto extends UserBaseDto {
    @NotEmpty(message = "email is required.")
    @NotNull(message = "email is required.")
    @Email(message = "please a valid email address")
    private String email;

}
