package com.example.orderservice.payment.exception;

import com.example.servicecommon.exception.core.CustomException;
import com.example.servicecommon.exception.core.ErrorCode;

public class InvalidPaymentException extends CustomException {
    public InvalidPaymentException() {
        super(ErrorCode.INVALID_PAYMENT);
    }
}
