package com.ticketland.ticketland.user.exception;

import com.ticketland.ticketland.global.exception.core.CustomException;
import com.ticketland.ticketland.global.exception.core.ErrorCode;

public class InvalidVerifyException extends CustomException {
    public InvalidVerifyException() {
        super(ErrorCode.INVALID_VERIFY_CODE);
    }
}
