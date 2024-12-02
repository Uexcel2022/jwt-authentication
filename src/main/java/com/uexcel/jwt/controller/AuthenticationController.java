package com.uexcel.jwt.controller;

import com.uexcel.jwt.dto.UserAuthenticationDto;
import com.uexcel.jwt.service.IUserAuthenticationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Validated
public class AuthenticationController {
    private final IUserAuthenticationService userAS;

    @PostMapping("/register")
    public ResponseEntity<String> authenticate(@Valid @RequestBody UserAuthenticationDto uAD) {
        userAS.register(uAD);
        return ResponseEntity.ok("Hello World");
    }
}
