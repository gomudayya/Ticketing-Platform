package com.example.showservice.venue.service;

import com.example.servicecommon.exception.NotFoundException;
import com.example.showservice.venue.domain.Venue;
import com.example.showservice.venue.dto.VenueCreateRequest;
import com.example.showservice.venue.dto.VenueDetailResponse;
import com.example.showservice.venue.dto.VenuePageResponse;
import com.example.showservice.venue.repository.VenueRepository;
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
    private final SeatService seatService;
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
        seatService.insertSeatInVenue(venue, seatCountDetails); // 좌석 테이블에 좌석정보 Insert. 나중에 비동기 처리 해줘야함.
        return VenueDetailResponse.from(venue);
    }

    public VenueDetailResponse findVenue(Long venueId) {
        Venue venue = venueRepository.findById(venueId)
                .orElseThrow(() -> new NotFoundException("공연 장소"));

        return VenueDetailResponse.from(venue);
    }

    public VenuePageResponse findVenue(String keyword, Pageable pageable) {
        System.out.println("keyword = " + keyword);
        Page<Venue> venuePage = venueRepository.findVenueByKeyword(keyword, pageable);
        return VenuePageResponse.from(venuePage);
    }
}
