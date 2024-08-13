package com.ticketland.ticketland.order.exception;

import com.ticketland.ticketland.global.exception.core.CustomException;
import com.ticketland.ticketland.global.exception.core.ErrorCode;

public class TicketSaleNotActiveException extends CustomException {
    public TicketSaleNotActiveException() {
        super(ErrorCode.TICKET_SALE_NOT_ACTIVE);
    }
}
