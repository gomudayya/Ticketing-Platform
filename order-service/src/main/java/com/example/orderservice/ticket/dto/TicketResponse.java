package com.example.orderservice.ticket.dto;

import com.example.orderservice.ticket.domain.Ticket;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TicketResponse {
    private Long ticketId;
    private Integer price;
    private String seatSection;
    private Integer seatNumber;

    public static TicketResponse from(Ticket ticket) {
        return builder()
                .ticketId(ticket.getId())
                .price(ticket.getPrice())
                .seatSection(ticket.getSeatSection())
                .seatNumber(ticket.getSeatNumber())
                .build();
    }
}
