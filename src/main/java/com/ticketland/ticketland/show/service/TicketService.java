package com.ticketland.ticketland.show.service;

import com.ticketland.ticketland.show.domain.Seat;
import com.ticketland.ticketland.show.domain.Show;
import com.ticketland.ticketland.show.domain.Ticket;
import com.ticketland.ticketland.show.domain.Venue;
import com.ticketland.ticketland.show.dto.TicketPriceDto;
import com.ticketland.ticketland.show.repository.SeatRepository;
import com.ticketland.ticketland.show.repository.TicketRepository;
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
            String seatSection = ticketPrice.getSeatSection();
            Integer price = ticketPrice.getPrice();

            List<Seat> seats = seatRepository.findByVenueIdAndSection(venue.getId(), seatSection); // 장소Id와 섹션을 통해 좌석을 조회한다.
            List<Ticket> tickets = seats.stream()
                    .map(seat -> createTicket(show, seat, price))
                    .toList();

            ticketRepository.saveAll(tickets);
        }
    }

    private Ticket createTicket(Show show, Seat seat, Integer price) {
        return Ticket.builder()
                .show(show)
                .seat(seat)
                .price(price)
                .build();
    }
}
