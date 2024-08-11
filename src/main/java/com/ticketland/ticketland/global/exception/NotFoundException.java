package com.ticketland.ticketland.global.exception;

import com.ticketland.ticketland.global.exception.core.CustomException;
import com.ticketland.ticketland.global.exception.core.ErrorCode;

public class NotFoundException extends CustomException {

    public NotFoundException(String notFoundResource) {
        super(ErrorCode.NOT_FOUND.getMsg().formatted(notFoundResource), ErrorCode.NOT_FOUND);
    }
}
