package com.ticketland.ticketland.user.exception;

import com.ticketland.ticketland.global.exception.core.CustomException;
import com.ticketland.ticketland.global.exception.core.ErrorCode;

public class AdminKeyUnmatchedException extends CustomException {

    public AdminKeyUnmatchedException() {
        super(ErrorCode.INVALID_ADMIN_KEY);
    }
}
