package com.ticketland.ticketland.global.exception.core;

import lombok.Getter;

@Getter
public class ExceptionResponse {
    private Integer statusCode;
    private String errorCode;
    private String msg;

    public ExceptionResponse(ErrorCode errorCode) {
        this.statusCode = errorCode.getStatusCode();
        this.errorCode = errorCode.name();
        this.msg = errorCode.getMsg();
    }

    public static ExceptionResponse of(ErrorCode errorCode) {
        return new ExceptionResponse(errorCode);
    }
}
