package com.ticketland.ticketland.user.exception;

import com.ticketland.ticketland.global.exception.CustomException;
import com.ticketland.ticketland.global.exception.ErrorCode;

public class InvalidVerifyException extends CustomException {
    public InvalidVerifyException() {
        super(ErrorCode.INVALID_VERIFY_CODE);
    }
}
