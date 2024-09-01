package com.example.orderservice.ticket.service;

import com.example.orderservice.client.showservice.ShowServiceClient;
import com.example.orderservice.client.showservice.dto.ShowSimpleResponse;
import com.example.orderservice.ticket.domain.Ticket;
import com.example.orderservice.ticket.dto.TicketAvailableResponse;
import com.example.orderservice.ticket.dto.TicketCreateRequest;
import com.example.orderservice.ticket.dto.TicketDetailResponse;
import com.example.orderservice.ticket.repository.TicketRepository;
import com.example.orderservice.ticket.repository.dto.TicketStatusDto;
import com.example.servicecommon.exception.CustomAccessDeniedException;
import com.example.servicecommon.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TicketService {

    private final TicketRepository ticketRepository;
    private final TicketCacheService ticketCacheService;
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

    public List<Ticket> findTickets(List<String> ticketCodes) {
        return ticketCodes.stream()
                .map(ticketCode -> ticketRepository.findByCode(ticketCode)
                        .orElseThrow(() -> new NotFoundException("티켓")))
                .toList();
    }
    public List<Ticket> findTicketsWithRock(List<String> ticketCodes) {
        log.info("비관적 락을 이용한 티켓 조회 발생");

        return ticketCodes.stream()
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

    public TicketAvailableResponse getTicketStatuses(Long showId) {
        Map<String, String> cachedTicketStatuses = ticketCacheService.getTicketStatuses(showId);
        if (!cachedTicketStatuses.isEmpty()) {
            System.out.println("---------CASH HIT----------");
            return TicketAvailableResponse.of(cachedTicketStatuses);
        }

        List<TicketStatusDto> ticketStatusList = ticketRepository.findTicketStatuses(showId);

        // <티켓 코드, 티켓 상태>;
        Map<String, String> ticketStatus = ticketStatusList.stream()
                .collect(Collectors.toMap(TicketStatusDto::getTicketCode, TicketStatusDto::getTicketStatusByString));

        System.out.println("------CASH MISS----------");
        return TicketAvailableResponse.of(ticketStatus);
    }
}
