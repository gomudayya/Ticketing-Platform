package com.example.servicecommon.exception;


import com.example.servicecommon.exception.core.CustomException;
import com.example.servicecommon.exception.core.ErrorCode;

public class NotFoundException extends CustomException {

    public NotFoundException(String notFoundResource) {
        super(ErrorCode.NOT_FOUND.getMsg().formatted(notFoundResource), ErrorCode.NOT_FOUND);
    }
}
