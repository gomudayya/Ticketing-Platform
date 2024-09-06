package com.example.servicecommon.exception.core;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.nio.ByteBuffer;

@Getter
@NoArgsConstructor
@Setter
public class ExceptionResponse {
    private Integer statusCode;
    private String errorCode;
    private String msg;

    private ExceptionResponse(ErrorCode errorCode) {
        this.statusCode = errorCode.getStatusCode();
        this.errorCode = errorCode.name();
        this.msg = errorCode.getMsg();
    }
    public ExceptionResponse(Integer statusCode, String errorCode, String msg) {
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.msg = msg;
    }

    public static ExceptionResponse of(ErrorCode errorCode) {
        return new ExceptionResponse(errorCode);
    }

    public static ExceptionResponse of(Integer statusCode, String errorCode, String msg) {
        return new ExceptionResponse(statusCode, errorCode, msg);
    }
}
