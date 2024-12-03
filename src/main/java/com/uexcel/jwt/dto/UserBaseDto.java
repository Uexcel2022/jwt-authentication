package com.uexcel.jwt.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "UserBase",description = "This schema will hold user name.")
@Getter @Setter
public class UserBaseDto {
    @NotEmpty(message = "First name is required.")
    @NotNull(message = "First name is required.")
    private String firstName;
    @NotEmpty(message = "Last name is required.")
    @NotNull(message = "Last name is required.")
    private String lastName;
}
