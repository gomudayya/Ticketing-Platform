package com.example.orderservice.order.exception;

import com.example.servicecommon.exception.core.CustomException;
import com.example.servicecommon.exception.core.ErrorCode;

public class InvalidRefundException extends CustomException {
    public InvalidRefundException() {
        super(ErrorCode.INVALID_REFUND);
    }

    public InvalidRefundException(String message) {
        super(message, ErrorCode.INVALID_REFUND);
    }

}
