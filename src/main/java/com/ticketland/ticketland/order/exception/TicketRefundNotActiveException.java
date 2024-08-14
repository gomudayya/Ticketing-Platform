package com.ticketland.ticketland.order.exception;

import com.ticketland.ticketland.global.exception.core.CustomException;
import com.ticketland.ticketland.global.exception.core.ErrorCode;

public class TicketRefundNotActiveException extends CustomException {
    public TicketRefundNotActiveException() {
        super(ErrorCode.TICKET_REFUND_NOT_ACTIVE);
    }
}
