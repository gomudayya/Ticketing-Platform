package com.ticketland.ticketland.user.exception;

import com.ticketland.ticketland.global.exception.core.CustomException;
import com.ticketland.ticketland.global.exception.core.ErrorCode;

public class VerifyExpiredException extends CustomException {
    public VerifyExpiredException() {
        super(ErrorCode.VERIFY_EXPIRED);
    }
}
