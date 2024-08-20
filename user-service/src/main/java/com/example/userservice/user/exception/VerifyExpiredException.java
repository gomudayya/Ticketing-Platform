package com.example.userservice.user.exception;

import com.example.servicecommon.exception.core.CustomException;
import com.example.servicecommon.exception.core.ErrorCode;

public class VerifyExpiredException extends CustomException {
    public VerifyExpiredException() {
        super(ErrorCode.VERIFY_EXPIRED);
    }
}
