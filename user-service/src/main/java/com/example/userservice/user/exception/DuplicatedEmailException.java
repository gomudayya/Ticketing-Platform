package com.example.userservice.user.exception;

import com.example.servicecommon.exception.core.CustomException;
import com.example.servicecommon.exception.core.ErrorCode;

public class DuplicatedEmailException extends CustomException {
    public DuplicatedEmailException() {
        super(ErrorCode.DUPLICATED_EMAIL);
    }
}
