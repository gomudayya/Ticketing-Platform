package com.example.orderservice.order.exception;

import com.example.servicecommon.exception.core.CustomException;
import com.example.servicecommon.exception.core.ErrorCode;

public class TicketUnavailableException extends CustomException {
    public TicketUnavailableException() {
        super(ErrorCode.TICKET_UNAVAILABLE);
    }
}
