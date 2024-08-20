package com.example.orderservice.order.exception;

import com.example.servicecommon.exception.core.CustomException;
import com.example.servicecommon.exception.core.ErrorCode;

public class TicketRefundNotActiveException extends CustomException {
    public TicketRefundNotActiveException() {
        super(ErrorCode.TICKET_REFUND_NOT_ACTIVE);
    }
}
