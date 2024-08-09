package com.ticketland.ticketland.user.exception;

import com.ticketland.ticketland.global.exception.CustomException;
import com.ticketland.ticketland.global.exception.ErrorCode;

public class VerifyExpiredException extends CustomException {
    public VerifyExpiredException() {
        super(ErrorCode.VERIFY_EXPIRED);
    }
}
