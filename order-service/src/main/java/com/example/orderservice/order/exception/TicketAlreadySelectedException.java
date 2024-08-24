package com.example.orderservice.order.exception;

import com.example.servicecommon.exception.core.CustomException;
import com.example.servicecommon.exception.core.ErrorCode;

public class TicketAlreadySelectedException extends CustomException {
    public TicketAlreadySelectedException() {
        super(ErrorCode.TICKET_ALREADY_SELECTED);
    }
}
