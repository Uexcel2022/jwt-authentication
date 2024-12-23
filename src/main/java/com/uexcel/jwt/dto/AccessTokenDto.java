package com.uexcel.jwt.dto;

import io.swagger.v3.oas.annotations.media.Schema;
@Schema(name = "AccessToken",description = "This schema will hold jwt access token")

public record AccessTokenDto(int status,String token) {
}
