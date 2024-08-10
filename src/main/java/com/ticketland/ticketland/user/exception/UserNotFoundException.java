package com.ticketland.ticketland.user.exception;

import com.ticketland.ticketland.global.exception.CustomException;
import com.ticketland.ticketland.global.exception.ErrorCode;

public class UserNotFoundException extends CustomException {

    public UserNotFoundException() {
        super(ErrorCode.INVALID_INPUT_FORMAT);
    }
}
