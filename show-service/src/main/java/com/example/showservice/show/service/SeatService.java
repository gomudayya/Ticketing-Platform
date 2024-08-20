package com.example.showservice.show.service;

import com.example.servicecommon.exception.NotFoundException;
import com.example.showservice.show.domain.Seat;
import com.example.showservice.show.repository.SeatRepository;
import com.example.showservice.show.domain.Venue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;

    public void insertSeatInVenue(Venue venue, Map<String, Integer> seatCountDetails) {
        for (String section : seatCountDetails.keySet()) {
            for (int seatNumber = 1; seatNumber <= seatCountDetails.get(section); seatNumber++) {
                Seat seat = Seat.builder()
                        .venue(venue)
                        .section(section)
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
