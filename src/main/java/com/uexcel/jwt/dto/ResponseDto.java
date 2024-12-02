package com.uexcel.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter @Setter
@AllArgsConstructor
public class ResponseDto {
    private String timestamp;
    private int status;
    private HttpStatus description;
    private String message;
    private String apiPath;
}
