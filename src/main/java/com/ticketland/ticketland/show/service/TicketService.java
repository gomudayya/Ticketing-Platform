package com.ticketland.ticketland.show.service;

import com.ticketland.ticketland.show.domain.Seat;
import com.ticketland.ticketland.show.domain.Show;
import com.ticketland.ticketland.show.domain.Ticket;
import com.ticketland.ticketland.show.domain.TicketPrice;
import com.ticketland.ticketland.show.domain.Venue;
import com.ticketland.ticketland.show.dto.TicketPriceDto;
import com.ticketland.ticketland.show.repository.SeatRepository;
import com.ticketland.ticketland.show.repository.TicketPriceRepository;
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
    private final TicketPriceRepository ticketPriceRepository;
    public void generateTickets(Show show, Venue venue, List<TicketPriceDto> ticketPrices) {
        for (TicketPriceDto ticketPrice : ticketPrices) {
            List<Seat> seats = seatRepository.findByVenueIdAndSection(venue.getId(), ticketPrice.getSeatSection()); // 장소Id와 섹션을 통해 좌석을 조회한다.
            List<Ticket> tickets = seats.stream()
                    .map(seat -> createTicket(show, seat, ticketPrice.getPrice()))
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

    public void saveTicketPrices(Show show, List<TicketPriceDto> ticketPriceDtos) {
        List<TicketPrice> ticketPrices = ticketPriceDtos.stream()
                .map(dto -> dto.toEntity(show))
                .toList();

        ticketPriceRepository.saveAll(ticketPrices);
    }
}
