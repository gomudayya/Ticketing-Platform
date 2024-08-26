package com.example.showservice.venue.repository.querydsl;

import com.example.showservice.show.dto.SeatCountDto;

import java.util.List;

public interface SeatRepositoryQuerydsl {

    List<SeatCountDto> findSeatCounts(Long venueId);

}
