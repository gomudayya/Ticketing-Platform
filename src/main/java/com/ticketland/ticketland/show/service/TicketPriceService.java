package com.ticketland.ticketland.show.service;

import com.ticketland.ticketland.show.domain.Show;
import com.ticketland.ticketland.ticket.dto.TicketPriceDto;
import com.ticketland.ticketland.show.repository.TicketPriceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TicketPriceService {
    private final TicketPriceRepository ticketPriceRepository;

    public void saveTicketPrices(Show show, List<TicketPriceDto> ticketPriceDtos) {
        ticketPriceDtos.stream()
                .map(dto -> dto.toEntity(show))
                .forEach(ticketPriceRepository::save);
    }
}
