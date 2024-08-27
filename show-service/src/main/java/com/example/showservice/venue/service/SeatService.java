package com.example.showservice.venue.service;

import com.example.servicecommon.exception.NotFoundException;
import com.example.showservice.show.dto.SeatCountDto;
import com.example.showservice.venue.domain.Seat;
import com.example.showservice.venue.repository.SeatRepository;
import com.example.showservice.venue.domain.Venue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;

    public void insertSeatInVenue(Venue venue, List<SeatCountDto> seatCountDetails) {
        for (SeatCountDto seatCountDto : seatCountDetails) {
            for (int seatNumber = 1; seatNumber <= seatCountDto.getCount(); seatNumber++) {
                Seat seat = Seat.builder()
                        .venue(venue)
                        .section(seatCountDto.getSection())
                        .number(seatNumber)
                        .build();

                seatRepository.save(seat);
            }
        }
    }

    public Seat findSeat(String section, Integer number) {
        return seatRepository.findBySectionAndNumber(section, number)
                .orElseThrow(() -> new NotFoundException("좌석"));
    }
}
