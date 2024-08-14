package com.ticketland.ticketland.global.exception;

import com.ticketland.ticketland.global.exception.core.CustomException;
import com.ticketland.ticketland.global.exception.core.ErrorCode;

public class CustomAccessDeniedException extends CustomException {
    public CustomAccessDeniedException() {
        super(ErrorCode.ACCESS_DENIED);
    }
}
