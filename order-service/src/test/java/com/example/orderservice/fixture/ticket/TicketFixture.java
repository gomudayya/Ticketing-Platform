package com.example.orderservice.fixture.ticket;

import com.example.orderservice.ticket.domain.Ticket;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TicketFixture {

    public static Ticket createSelectedTicket(Long userId) {
        Ticket ticket = Ticket.builder()
                .code(UUID.randomUUID().toString())
                .price(123)
                .build();

        ticket.isSelectedBy(userId);
        return ticket;
    }

    public static List<Ticket> createTicketsWithShowId(Long showId, int count) {
        List<Ticket> result = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Ticket ticket = Ticket.builder()
                    .price(12345)
                    .code(Ticket.makeCode(showId, "A", 123 + i))
                    .build();

            result.add(ticket);
        }

        return result;
    }
}
