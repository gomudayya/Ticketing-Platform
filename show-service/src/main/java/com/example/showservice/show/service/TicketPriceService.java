package com.example.showservice.show.service;

import com.example.showservice.show.domain.Show;
import com.example.showservice.show.dto.TicketPriceDto;
import com.example.showservice.show.repository.TicketPriceRepository;
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
