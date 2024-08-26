package com.example.orderservice.ticket.repository.querydsl;

import com.example.orderservice.ticket.repository.dto.TicketStatusDto;

import java.util.List;

public interface TicketRepositoryQuerydsl {

    List<TicketStatusDto> findTicketStatus(Long showId, String section);
}
