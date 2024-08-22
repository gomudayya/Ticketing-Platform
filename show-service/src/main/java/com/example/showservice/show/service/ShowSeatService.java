package com.example.showservice.show.service;

import com.example.showservice.show.domain.Show;
import com.example.showservice.show.domain.ShowSeat;
import com.example.showservice.show.dto.SeatPriceDto;
import com.example.showservice.show.dto.seat.SeatCountDto;
import com.example.showservice.show.repository.ShowSeatRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ShowSeatService {
    private final ShowSeatRepository showSeatRepository;

    public void saveShowSeatInfo(Show show,
                                 List<SeatPriceDto> seatPrices,
                                 List<SeatCountDto> seatCounts) {
        Map<String, Integer> seatCountMap = new HashMap<>();
        seatCounts.forEach(seatCount -> seatCountMap.put(seatCount.getSection(), seatCount.getCount().intValue()));

        for (SeatPriceDto showPriceDto : seatPrices) {
            ShowSeat showSeat = ShowSeat.builder()
                    .show(show)
                    .seatSection(showPriceDto.getSeatSection())
                    .price(showPriceDto.getPrice())
                    .stock(seatCountMap.get(showPriceDto.getSeatSection()))
                    .build();

            showSeatRepository.save(showSeat);
        }
    }
}
