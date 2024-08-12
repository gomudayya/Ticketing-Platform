package com.ticketland.ticketland.show.service;

import com.ticketland.ticketland.global.exception.NotFoundException;
import com.ticketland.ticketland.show.domain.Seat;
import com.ticketland.ticketland.show.domain.Venue;
import com.ticketland.ticketland.show.dto.venue.VenueCreateRequest;
import com.ticketland.ticketland.show.dto.venue.VenueDetailResponse;
import com.ticketland.ticketland.show.dto.venue.VenuePageResponse;
import com.ticketland.ticketland.show.repository.SeatRepository;
import com.ticketland.ticketland.show.repository.VenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class VenueService {

    private final VenueRepository venueRepository;
    private final SeatRepository seatRepository;

    public VenueDetailResponse createVenue(VenueCreateRequest request) {
        Map<String, Integer> seatCountDetails = request.getSeatCountDetails(); // <Section, 해당 Section 의 좌석갯수>
        int seatCount = seatCountDetails.values().stream().mapToInt(n -> n).sum();

        Venue venue = Venue.builder()
                .venueName(request.getVenueName())
                .address(request.getAddress())
                .seatLayoutData(request.getSeatLayoutData())
                .seatCount(seatCount)
                .build();

        venueRepository.save(venue);
        insertSeatInVenue(venue, seatCountDetails); // 좌석 테이블에 좌석정보 Insert
        return VenueDetailResponse.from(venue);
    }

    private void insertSeatInVenue(Venue venue, Map<String, Integer> seatCountDetails) {
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

    public VenueDetailResponse findVenue(Long venueId) {
        Venue venue = venueRepository.findById(venueId)
                .orElseThrow(() -> new NotFoundException("공연 장소"));

        return VenueDetailResponse.from(venue);
    }

    public VenuePageResponse findVenue(String keyword, Pageable pageable) {
        Page<Venue> venuePage = venueRepository.findVenueByKeyword(keyword, pageable);
        return VenuePageResponse.from(venuePage);
    }
}
