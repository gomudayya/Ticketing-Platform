package com.ticketland.ticketland.ticket.service;

import com.ticketland.ticketland.global.exception.NotFoundException;
import com.ticketland.ticketland.show.domain.Seat;
import com.ticketland.ticketland.show.domain.Show;
import com.ticketland.ticketland.ticket.domain.Ticket;
import com.ticketland.ticketland.show.domain.Venue;
import com.ticketland.ticketland.ticket.dto.TicketPriceDto;
import com.ticketland.ticketland.show.repository.SeatRepository;
import com.ticketland.ticketland.ticket.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TicketService {

    private final SeatRepository seatRepository;
    private final TicketRepository ticketRepository;
    public void generateTickets(Show show, Venue venue, List<TicketPriceDto> ticketPrices) {
        for (TicketPriceDto ticketPrice : ticketPrices) {
            List<Seat> seats = seatRepository.findByVenueIdAndSection(venue.getId(), ticketPrice.getSeatSection()); // 장소Id와 섹션을 통해 좌석을 조회한다.
            seats.stream()
                    .map(seat -> createTicket(show, seat, ticketPrice.getPrice()))
                    .forEach(ticketRepository::save);

        }
    }
    private Ticket createTicket(Show show, Seat seat, Integer price) {
        return Ticket.builder()
                .id(generateTicketId(show.getId(), seat.getSection(), seat.getNumber()))
                .show(show)
                .price(price)
                .build();
    }

    private String generateTicketId(Long showId, String seatSection, Integer seatNumber) {
        return String.format("%d_%s_%d", showId, seatSection, seatNumber); // TicketId = {공연Id}_{좌석섹션}_{좌석번호}
    }

    public Ticket findTicket(Long showId, String seatSection, Integer seatNumber) {
        return ticketRepository.findById(generateTicketId(showId, seatSection, seatNumber))
                .orElseThrow(() -> new NotFoundException("티켓"));
    }
}
