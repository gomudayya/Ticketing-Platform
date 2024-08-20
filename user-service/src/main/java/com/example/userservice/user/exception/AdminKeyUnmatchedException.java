package com.example.userservice.user.exception;


import com.example.servicecommon.exception.core.CustomException;
import com.example.servicecommon.exception.core.ErrorCode;

public class AdminKeyUnmatchedException extends CustomException {

    public AdminKeyUnmatchedException() {
        super(ErrorCode.INVALID_ADMIN_KEY);
    }
}
