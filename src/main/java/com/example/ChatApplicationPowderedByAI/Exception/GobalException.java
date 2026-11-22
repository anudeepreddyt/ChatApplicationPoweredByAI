package com.example.ChatApplicationPowderedByAI.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GobalException {

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleAlreadyExistException(AlreadyExistException e){
        ErrorResponse errorResponse=new ErrorResponse();
        errorResponse.setStatus(HttpStatus.FORBIDDEN.value());
        errorResponse.setMessage(e.getMessage());
        errorResponse.setLocalDateTime(LocalDateTime.now());
        return ResponseEntity.ok(errorResponse);
    }

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ErrorResponse> handleCommonException(CommonException e){
        ErrorResponse errorResponse=new ErrorResponse();
        errorResponse.setLocalDateTime(LocalDateTime.now());
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setMessage(e.getMessage());
        return ResponseEntity.ok(errorResponse);
    }
}
