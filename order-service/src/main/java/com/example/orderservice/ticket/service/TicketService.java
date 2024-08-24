package com.example.orderservice.ticket.service;

import com.example.orderservice.client.showservice.ShowServiceClient;
import com.example.orderservice.client.showservice.dto.ShowSimpleResponse;
import com.example.orderservice.order.dto.SeatDto;
import com.example.orderservice.ticket.domain.Ticket;
import com.example.orderservice.ticket.dto.TicketCreateRequest;
import com.example.orderservice.ticket.dto.TicketDetailResponse;
import com.example.orderservice.ticket.repository.TicketRepository;
import com.example.servicecommon.exception.CustomAccessDeniedException;
import com.example.servicecommon.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final ShowServiceClient showServiceClient;

    public void generateTickets(TicketCreateRequest request) {
        for (var seatInfo : request.getSeatInfo()) {
            for (int seatNumber = 1; seatNumber <= seatInfo.getCount(); seatNumber++) {
                Ticket ticket = Ticket.builder()
                        .price(seatInfo.getPrice())
                        .code(Ticket.makeCode(request.getShowId(), seatInfo.getSection(), seatNumber))
                        .build();

                ticketRepository.save(ticket);
            }
        }
    }

    public List<Ticket> findTicketsWithRock(Long showId, List<SeatDto> seats) {
        return seats.stream()
                .map(seat -> Ticket.makeCode(showId, seat.getSection(), seat.getNumber()))
                .map(ticketCode -> ticketRepository.findByCodeWithLock(ticketCode)
                        .orElseThrow(() -> new NotFoundException("티켓")))
                .toList();
    }

    public List<TicketDetailResponse> findTicket(Long userId, List<Long> ticketIds) {
        List<Ticket> tickets = ticketRepository.findByIdIn(ticketIds);

        if (!tickets.get(0).isOwnedBy(userId)) {
            throw new CustomAccessDeniedException();
        }

        List<Long> showIds = tickets.stream().map(Ticket::getShowId).toList();
        List<ShowSimpleResponse> shows = showServiceClient.getShows(showIds);

        Map<Long, ShowSimpleResponse> showMap = shows.stream()   //<공연 id, 공연 응답>
                .collect(Collectors.toMap(ShowSimpleResponse::getShowId, Function.identity()));

        return tickets.stream()
                .map(ticket -> TicketDetailResponse.from(ticket, showMap.get(ticket.getShowId())))
                .collect(Collectors.toList());
    }


}
