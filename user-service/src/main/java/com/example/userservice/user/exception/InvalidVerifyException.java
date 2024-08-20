package com.example.userservice.user.exception;

import com.example.servicecommon.exception.core.CustomException;
import com.example.servicecommon.exception.core.ErrorCode;

public class InvalidVerifyException extends CustomException {
    public InvalidVerifyException() {
        super(ErrorCode.INVALID_VERIFY_CODE);
    }
}
