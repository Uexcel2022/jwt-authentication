package com.uexcel.jwt.controller;

import com.uexcel.jwt.constant.AppConstants;
import com.uexcel.jwt.dto.*;
import com.uexcel.jwt.service.IUserAuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
@Tag(name = "CRUD REST APIs for JWT Demo",
        description = "REST APIs Demonstration JWT Authentication and Authorization"
)

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Validated
public class AuthenticationController {
    private final IUserAuthenticationService userAS;

    @Operation(
            summary = "REST API To Create User Details",
            description = "REST API to create user details for JWT Demo",
            responses = {
                    @ApiResponse(
                            responseCode = "201", description = "Ok",
                            content = @Content(schema = @Schema(implementation = ResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400", description = "Bad Request",
                            content = @Content(schema = @Schema(implementation = ResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "500", description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ResponseDto.class))
                    )
            }
    )

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> authenticate(@Valid @RequestBody UserAuthenticationDto uAD) {
        ResponseDto resp = userAS.register(uAD);
        return ResponseEntity.status(resp.getStatus()).body(resp);
    }

    @Operation(
            summary = "REST API To Login",
            description = "REST API to login to obtain JWT access token",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Ok",
                            content = @Content(schema = @Schema(implementation = ResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400", description = "Bad Request",
                            content = @Content(schema = @Schema(implementation = ResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "500", description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ResponseDto.class))
                    )
            }
    )

    @PostMapping("/login")
    public ResponseEntity<AccessTokenDto> authenticate(@Valid @RequestBody LoginDto loginDto) {
        AccessTokenDto resp = userAS.authenticate(loginDto);
        return ResponseEntity.ok().header(AppConstants.HEADER,resp.token()).body(resp);
    }

    @Operation(
            summary = "REST API To Fetch User",
            description = "REST API to fetch user with JWT access token",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Ok",
                            content = @Content(schema = @Schema(implementation = ResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400", description = "Bad Request",
                            content = @Content(schema = @Schema(implementation = ResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "403", description = "Forbidden"
                    ),
                    @ApiResponse(
                            responseCode = "404", description = "Not Found",
                            content = @Content(schema = @Schema(implementation = ResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "500", description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ResponseDto.class))
                    )
            }
    )

    @GetMapping("/get-user")
    public ResponseEntity<UserResponseDto> fetchUser(@RequestParam String email) {
        UserResponseDto uRD = userAS.fetchUserByEmail(email);
        return ResponseEntity.ok().body(uRD);
    }

    @Operation(
            summary = "REST API To Fetch User",
            description = "REST API to fetch user with JWT access token",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Ok",
                            content = @Content(schema = @Schema(implementation = ResponseDto.class))
                    ),

                    @ApiResponse(
                            responseCode = "403", description = "Forbidden"
                    ),

                    @ApiResponse(
                            responseCode = "500", description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ResponseDto.class))
                    )
            }
    )

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateUser(@RequestBody UserBaseDto uBD) {
        ResponseDto uRD = userAS.updateUser(uBD);
        return ResponseEntity.ok().body(uRD);
    }

}
