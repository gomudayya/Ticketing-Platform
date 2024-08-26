package com.example.orderservice.ticket.dto;

import com.example.orderservice.ticket.constant.TicketStatus;
import com.example.orderservice.ticket.repository.dto.TicketStatusDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TicketAvailableResponse {

    public List<Ticket> tickets = new ArrayList<>();

    public static TicketAvailableResponse of(List<TicketStatusDto> ticketStatusDtos) {
        List<Ticket> ticketSelect = ticketStatusDtos.stream()
                .map(Ticket::of)
                .toList();

        return new TicketAvailableResponse(ticketSelect);
    }

    @Getter
    @Setter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Ticket {
        public String ticketCode;
        public Boolean isAvailable;

        public static Ticket of(TicketStatusDto ticketStatusDto) {
            if (ticketStatusDto.getTicketStatus() == TicketStatus.AVAILABLE) {
                return new Ticket(ticketStatusDto.getTicketCode(), true);
            }

            return new Ticket(ticketStatusDto.getTicketCode(), false);
        }
    }
}
