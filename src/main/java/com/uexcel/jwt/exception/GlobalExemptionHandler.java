package com.uexcel.jwt.exception;

import com.uexcel.jwt.dto.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.uexcel.jwt.service.IUserAuthenticationService.getTime;

@ControllerAdvice
public class GlobalExemptionHandler extends ResponseEntityExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExemptionHandler.class);

    @Nullable
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders
            headers, HttpStatusCode status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();

        List<FieldError> errors = ex.getBindingResult().getFieldErrors();
        errors.forEach(error -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            body.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);

    }
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
