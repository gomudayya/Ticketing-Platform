package com.example.orderservice.ticket.repository.querydsl;


import com.example.orderservice.ticket.domain.QTicket;
import com.example.orderservice.ticket.repository.dto.TicketStatusDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.orderservice.ticket.domain.QTicket.ticket;

@RequiredArgsConstructor
public class TicketRepositoryQuerydslImpl implements TicketRepositoryQuerydsl{

    private final JPAQueryFactory queryFactory;
    @Override
    public List<TicketStatusDto> findTicketStatus(Long showId, String section) {
        return queryFactory
                .select(Projections.bean(
                        TicketStatusDto.class,
                        ticket.code.as("ticketCode"),
                        ticket.ticketStatus.as("ticketStatus")
                ))
                .from(ticket)
                .where(ticket.code.startsWith(String.valueOf(showId).concat("_").concat(section).concat("_")))
                .fetch();
    }
}
