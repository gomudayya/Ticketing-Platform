package com.example.servicecommon.exception.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Collection;
import java.util.Map;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ObjectMapper objectMapper;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleUnexpectedException(Exception e) {
        e.printStackTrace();

        return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getStatusCode())
                .body(ExceptionResponse.of(ErrorCode.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ExceptionResponse> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        printExceptionLog(e);

        return ResponseEntity.status(ErrorCode.NOT_SUPPORTED_CONTENT_TYPE.getStatusCode())
                .body(ExceptionResponse.of(ErrorCode.NOT_SUPPORTED_CONTENT_TYPE));
    }


    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ExceptionResponse> handleRequestMethodNotSupported(HttpRequestMethodNotSupportedException e) {
        printExceptionLog(e);

        return ResponseEntity.status(ErrorCode.NOT_SUPPORTED_HTTP_METHOD.getStatusCode())
                .body(ExceptionResponse.of(ErrorCode.NOT_SUPPORTED_HTTP_METHOD));
    }

    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseEntity<ExceptionResponse> handleHttpMessageConversionException(HttpMessageConversionException e) {
        printExceptionLog(e);

        return ResponseEntity.badRequest()
                .body(ExceptionResponse.of(ErrorCode.INVALID_INPUT_FORMAT));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ExceptionResponse> handleBindException(BindException e) {
        printExceptionLog(e);

        String bindingErrorMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        ErrorCode errorCode = ErrorCode.INVALID_INPUT_FORMAT;

        return ResponseEntity.badRequest()
                .body(ExceptionResponse.of(errorCode.getStatusCode(), errorCode.name(), bindingErrorMessage));
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ExceptionResponse> handleFeignException(FeignException e) {
        ByteBuffer byteBuffer = e.responseBody()
                .orElseThrow(() -> new IllegalStateException("Error Response 의 Body 가 존재하지 않습니다."));

        byte[] byteArray = new byte[byteBuffer.remaining()];
        byteBuffer.get(byteArray);
        String content = new String(byteArray, StandardCharsets.UTF_8);

        try {
            System.out.println("content = " + content);
            ExceptionResponse exceptionResponse = objectMapper.readValue(content, ExceptionResponse.class);

            return ResponseEntity
                    .status(exceptionResponse.getStatusCode())
                    .body(exceptionResponse);
        } catch (JsonProcessingException ex) {
            throw new IllegalStateException("다른 서비스의 에러 응답 포맷이 올바르지 않습니다.");
        }
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionResponse> handleCustomException(CustomException e) {
        printExceptionLog(e);

        ErrorCode errorCode = e.getErrorCode();
        if (!e.getMessage().equals(errorCode.getMsg())) {
            // 에러코드 메시지와 예외 메시지가 다를경우에는 예외 메시지가 우선한다.
            return ResponseEntity.status(errorCode.getStatusCode())
                            .body(ExceptionResponse.of(errorCode.getStatusCode(), errorCode.name(), e.getMessage()));
        }

        return ResponseEntity.status(errorCode.getStatusCode())
                .body(ExceptionResponse.of(errorCode));
    }

    private void printExceptionLog(Exception e) {
        log.info("[{}] 예외 발생: {}", e.getClass().getName(), e.getMessage());
    }
}
