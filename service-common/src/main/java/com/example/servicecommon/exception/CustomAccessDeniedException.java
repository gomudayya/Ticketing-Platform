package com.example.servicecommon.exception;


import com.example.servicecommon.exception.core.CustomException;
import com.example.servicecommon.exception.core.ErrorCode;

public class CustomAccessDeniedException extends CustomException {
    public CustomAccessDeniedException() {
        super(ErrorCode.ACCESS_DENIED);
    }
}
