package com.example.orderservice.ticket.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TicketAvailableResponse {

    private Map<String, String> ticketStatuses;

    public static TicketAvailableResponse of(Map<String, String> ticketStatuses) {
        return new TicketAvailableResponse(ticketStatuses);
    }

}
