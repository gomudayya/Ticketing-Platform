package com.example.orderservice.order.exception;

import com.example.servicecommon.exception.core.CustomException;
import com.example.servicecommon.exception.core.ErrorCode;

public class TicketSaleNotActiveException extends CustomException {
    public TicketSaleNotActiveException() {
        super(ErrorCode.TICKET_SALE_NOT_ACTIVE);
    }
}
