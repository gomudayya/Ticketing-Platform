package com.ticketland.ticketland.user.exception;

import com.ticketland.ticketland.global.exception.core.CustomException;
import com.ticketland.ticketland.global.exception.core.ErrorCode;

public class UserNotFoundException extends CustomException {

    public UserNotFoundException() {
        super(ErrorCode.INVALID_INPUT_FORMAT);
    }
}
