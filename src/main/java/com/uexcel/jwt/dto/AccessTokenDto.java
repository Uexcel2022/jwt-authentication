package com.uexcel.jwt.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
@Schema(name = "AccessToken",description = "This schema will hold jwt access token")
@Getter
@AllArgsConstructor
public class AccessTokenDto {
    private String token;
}
