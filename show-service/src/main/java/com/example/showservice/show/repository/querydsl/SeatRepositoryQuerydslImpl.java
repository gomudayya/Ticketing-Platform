package com.example.showservice.show.repository.querydsl;

import com.example.showservice.show.domain.QSeat;
import com.example.showservice.show.dto.TicketCreateRequest;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.showservice.show.domain.QSeat.seat;

@RequiredArgsConstructor
public class SeatRepositoryQuerydslImpl implements SeatRepositoryQuerydsl{

    private final JPAQueryFactory queryFactory;
    @Override
    public List<TicketCreateRequest.SeatInfo> findSeatCounts(Long venueId) {
        return queryFactory
                .select(Projections.bean(
                        TicketCreateRequest.SeatInfo.class,
                        seat.section.as("section"),
                        seat.id.count().as("count")
                ))
                .from(seat)
                .where(seat.venue.id.eq(venueId))
                .groupBy(seat.section)
                .fetch();
    }
}
