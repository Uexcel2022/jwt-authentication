package com.uexcel.jwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @GetMapping("/success")
    public ResponseEntity<String> login() {
        return   ResponseEntity.ok().body("{\"message\": \"success\"}");
    }
}
