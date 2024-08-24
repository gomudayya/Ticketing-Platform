package com.example.orderservice.ticket.domain;

import com.example.orderservice.ticket.constant.TicketStatus;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "ticket")
public class CachedTicket {

    @Id
    private final String ticketCode;

    private TicketStatus ticketStatus;

    private Long userId;

    private CachedTicket(String ticketCode, TicketStatus ticketStatus) {
        this.ticketCode = ticketCode;
        this.ticketStatus = ticketStatus;
    }

    public static CachedTicket from(Ticket ticket) {
        return new CachedTicket(ticket.getCode(), ticket.getTicketStatus());
    }
    public void isReservedBy(Long userId) {
        ticketStatus = TicketStatus.SELECTED;
        this.userId = userId;
    }

    public boolean isReserved() {
        return ticketStatus == TicketStatus.SELECTED || ticketStatus == TicketStatus.SOLD;
    }
}
