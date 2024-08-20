package com.example.orderservice.ticket.service;

import com.example.orderservice.ticket.dto.TicketCreateRequest;
import com.example.orderservice.ticket.repository.TicketRepository;
import com.example.orderservice.ticket.domain.Ticket;
import com.example.servicecommon.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    public void generateTickets(TicketCreateRequest request) {
        for (var seatInfo : request.getSeatInfos()) {
            for (int seatNumber = 1; seatNumber <= seatInfo.getCount() ; seatNumber++) {
                Ticket ticket = Ticket.builder()
                        .price(seatInfo.getPrice())
                        .code(Ticket.makeCode(request.getShowId(), seatInfo.getSection(), seatNumber))
                        .build();

                ticketRepository.save(ticket);
            }
        }
    }
//    public void generateTickets(Show show, Venue venue, List<TicketPriceDto> ticketPrices) {
//        for (TicketPriceDto ticketPrice : ticketPrices) {
//            List<Seat> seats = seatRepository.findByVenueIdAndSection(venue.getId(), ticketPrice.getSeatSection()); // 장소Id와 섹션을 통해 좌석을 조회한다.
//            seats.stream()
//                    .map(seat -> createTicket(show, seat, ticketPrice.getPrice()))
//                    .forEach(ticketRepository::save);

//        }
//    }
//    private Ticket createTicket(Show show, Seat seat, Integer price) {
//        return Ticket.builder()
//                .id(generateTicketId(show.getId(), seat.getSection(), seat.getNumber()))
//                .show(show)
//                .price(price)
//                .build();
//    }

    public Ticket findTicket(Long showId, String seatSection, Integer seatNumber) {
        return ticketRepository.findByCode(Ticket.makeCode(showId, seatSection, seatNumber))
                .orElseThrow(() -> new NotFoundException("티켓"));
    }
}
