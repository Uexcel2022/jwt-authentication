package com.uexcel.jwt.controller;

import com.uexcel.jwt.dto.LoginDto;
import com.uexcel.jwt.dto.ResponseDto;
import com.uexcel.jwt.dto.UserAuthenticationDto;
import com.uexcel.jwt.dto.UserTokenDto;
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
    public ResponseEntity<ResponseDto> authenticate(@Valid @RequestBody UserAuthenticationDto uAD) {
        ResponseDto resp = userAS.register(uAD);
        return ResponseEntity.status(resp.getStatus()).body(resp);
    }

    @PostMapping("/login")
    public ResponseEntity<UserTokenDto> authenticate(@Valid @RequestBody LoginDto loginDto) {
        UserTokenDto resp = userAS.authenticate(loginDto);
        return ResponseEntity.ok().body(resp);
    }
}
