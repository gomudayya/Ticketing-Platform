package com.example.orderservice.ticket.service;

import com.example.orderservice.ticket.dto.TicketCreateRequest;
import com.example.orderservice.ticket.repository.TicketRepository;
import com.example.orderservice.ticket.domain.Ticket;
import com.example.servicecommon.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    public void generateTickets(TicketCreateRequest request) {
        for (var seatInfo : request.getSeatInfo()) {
            for (int seatNumber = 1; seatNumber <= seatInfo.getCount() ; seatNumber++) {
                Ticket ticket = Ticket.builder()
                        .price(seatInfo.getPrice())
                        .code(Ticket.makeCode(request.getShowId(), seatInfo.getSection(), seatNumber))
                        .build();

                ticketRepository.save(ticket);
            }
        }
    }

    public Ticket findTicket(Long showId, String seatSection, Integer seatNumber) {
        return ticketRepository.findByCode(Ticket.makeCode(showId, seatSection, seatNumber))
                .orElseThrow(() -> new NotFoundException("티켓"));
    }
}
