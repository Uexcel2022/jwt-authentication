package com.uexcel.jwt.dto.exception;

import com.uexcel.jwt.dto.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import static com.uexcel.jwt.service.IUserAuthenticationService.getTime;

@ControllerAdvice
public class GlobalExemptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalExemptionHandler.class);
    @ExceptionHandler(AppExceptions.class)
    public ResponseEntity<ResponseDto> handleAppExceptions(AppExceptions ex, WebRequest request){
     return  ResponseEntity.status(ex.getStatus()).body(
                new ResponseDto(getTime(),ex.getStatus(),
                        ex.getDescription(),ex.getMessage(),request.getDescription(false))
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto> handleAppExceptions(Exception ex, WebRequest request){
        logger.debug(ex.getMessage(), ex);
        return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ResponseDto(getTime(),500,
                        HttpStatus.INTERNAL_SERVER_ERROR,ex.getMessage(), request.getDescription(false))
        );
    }

}
