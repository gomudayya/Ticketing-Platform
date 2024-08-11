package com.ticketland.ticketland.global.exception;

import com.ticketland.ticketland.global.exception.core.CustomException;
import com.ticketland.ticketland.global.exception.core.ErrorCode;

public class NotFoundException extends CustomException {

    public NotFoundException(String message) {
        super(message, ErrorCode.NOT_FOUND);
    }
}
