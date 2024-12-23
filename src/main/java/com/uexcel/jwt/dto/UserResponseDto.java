package com.uexcel.jwt.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.annotation.Order;

@Schema(name = "UserResponse",description = "This schema will hold user id and role in addition to other user info.")
@Getter @Setter
public class UserResponseDto extends EmailDto {
    @NotEmpty
    @NotNull
    @Order(1)
    private String id;
    @NotEmpty
    @NotNull
    private String role;
}
