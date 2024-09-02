package com.example.orderservice.fixture.ticket;

import com.example.orderservice.ticket.domain.Ticket;
import net.bytebuddy.utility.RandomString;

public class TicketFixture {

    public static Ticket createSelectedTicket(Long userId) {
        Ticket ticket = Ticket.builder()
                .code(RandomString.make(10))
                .price(123)
                .build();

        ticket.isSelectedBy(userId);
        return ticket;
    }
}
