package com.example.showservice.venue.repository.querydsl;

import com.example.showservice.venue.domain.Venue;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.example.showservice.venue.domain.QVenue.venue;


@RequiredArgsConstructor
public class VenueRepositoryQuerydslImpl implements VenueRepositoryQuerydsl{

    private final JPAQueryFactory queryFactory;
    @Override
    public Page<Venue> findVenueByKeyword(String keyword, Pageable pageable) {
        List<Venue> venues = queryFactory.selectFrom(venue)
                .where(containsKeyword(keyword))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory.select(venue.count()).from(venue)
                .where(venue.venueName.contains(keyword)
                        .or(venue.address.contains(keyword)))
                .fetchFirst();

        return new PageImpl<>(venues, pageable, total);
    }

    private BooleanExpression containsKeyword(String keyword) {
        return StringUtils.isEmpty(keyword) ? null : venue.venueName.contains(keyword).or(venue.address.contains(keyword));
    }
}
