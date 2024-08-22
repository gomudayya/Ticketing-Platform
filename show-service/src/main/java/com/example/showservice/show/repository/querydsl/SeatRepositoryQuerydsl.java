package com.example.showservice.show.repository.querydsl;

import com.example.showservice.client.orderservice.dto.TicketCreateRequest;
import com.example.showservice.show.dto.seat.SeatCountDto;

import java.util.List;

public interface SeatRepositoryQuerydsl {

    List<SeatCountDto> findSeatCounts(Long venueId);

}
