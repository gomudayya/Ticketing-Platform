package com.example.showservice.show.repository.querydsl;

import com.example.showservice.show.dto.TicketCreateRequest;

import java.util.List;

public interface SeatRepositoryQuerydsl {

    List<TicketCreateRequest.SeatInfo> findSeatCounts(Long venueId);

}
