package com.example.orderservice.ticket.service;

import com.example.orderservice.order.dto.SeatDto;
import com.example.orderservice.ticket.domain.CachedTicket;
import com.example.orderservice.ticket.domain.Ticket;
import com.example.orderservice.ticket.repository.TicketCacheRepository;
import com.example.orderservice.ticket.repository.TicketRepository;
import com.example.servicecommon.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TicketCacheService {
    private final TicketCacheRepository ticketCacheRepository;
    private final TicketRepository ticketRepository;



    // (티켓팅 시간이 임박한) 티켓들을 Redis에 캐싱한다.
    public void cacheTickets(Long showId) {
        List<Ticket> tickets = ticketRepository.findTicketByShowId(showId);
        List<CachedTicket> cachedTickets = tickets.stream()
                .map(CachedTicket::from)
                .toList();

        ticketCacheRepository.saveAll(cachedTickets);
    }

    public List<CachedTicket> findCachedTickets(Long showId, List<SeatDto> seats) {
        List<CachedTicket> cachedTickets = new ArrayList<>();
        for (SeatDto seat : seats) {
            String ticketCode = Ticket.makeCode(showId, seat.getSection(), seat.getNumber());
            ticketCacheRepository.findById(ticketCode).ifPresent(cachedTickets::add);
        }

        return cachedTickets;
    }
}
