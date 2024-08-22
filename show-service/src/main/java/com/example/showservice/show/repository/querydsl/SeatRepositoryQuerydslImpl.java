package com.example.showservice.show.repository.querydsl;

import com.example.showservice.client.orderservice.dto.TicketCreateRequest;
import com.example.showservice.show.dto.seat.SeatCountDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.showservice.show.domain.QSeat.seat;

@RequiredArgsConstructor
public class SeatRepositoryQuerydslImpl implements SeatRepositoryQuerydsl{

    private final JPAQueryFactory queryFactory;
    @Override
    public List<SeatCountDto> findSeatCounts(Long venueId) {
        return queryFactory
                .select(Projections.bean(
                        SeatCountDto.class,
                        seat.section.as("section"),
                        seat.id.count().as("count")
                ))
                .from(seat)
                .where(seat.venue.id.eq(venueId))
                .groupBy(seat.section)
                .fetch();
    }
}
