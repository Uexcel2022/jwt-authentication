package com.uexcel.jwt.dto.exception;

import lombok.Getter;

import org.springframework.http.HttpStatus;

@Getter
public class AppExceptions extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private int status;
    private HttpStatus description;
    public AppExceptions(int status, HttpStatus description, String message) {
        super(message);
        this.status = status;
        this.description = description;
    }
}
