package com.ticketland.ticketland.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseEntity<ExceptionResponse> handleHttpMessageConversionException(HttpMessageConversionException e) {
        printExceptionLog(e);

        return ResponseEntity.badRequest()
                .body(ExceptionResponse.of(ErrorCode.INVALID_INPUT_FORMAT));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ExceptionResponse> handleBindException(BindException e) {
        printExceptionLog(e);

        String defaultMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        ErrorCode errorCode = ErrorCode.INVALID_INPUT_FORMAT;
        errorCode.changeMsg(defaultMessage);

        return ResponseEntity.badRequest()
                .body(ExceptionResponse.of(errorCode));
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionResponse> handleCustomException(CustomException e) {
        printExceptionLog(e);

        ErrorCode errorCode = e.getErrorCode();

        return ResponseEntity.status(errorCode.getStatusCode())
                .body(ExceptionResponse.of(errorCode));
    }

    private void printExceptionLog(Exception e) {
        log.info("[{}] 예외 발생: {}", e.getClass().getName(), e.getMessage());
    }
}
