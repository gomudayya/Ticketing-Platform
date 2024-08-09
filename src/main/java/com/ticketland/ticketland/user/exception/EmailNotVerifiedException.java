package com.ticketland.ticketland.user.exception;

import com.ticketland.ticketland.global.exception.CustomException;
import com.ticketland.ticketland.global.exception.ErrorCode;

public class EmailNotVerifiedException extends CustomException {

    public EmailNotVerifiedException() {
        super(ErrorCode.EMAIL_NOT_VERIFIED);
    }
}
