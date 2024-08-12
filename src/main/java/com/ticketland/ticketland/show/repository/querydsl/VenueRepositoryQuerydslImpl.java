package com.ticketland.ticketland.show.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ticketland.ticketland.show.domain.Venue;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.ticketland.ticketland.show.domain.QVenue.venue;
import static com.ticketland.ticketland.wishlist.domain.QWishlist.wishlist;

@RequiredArgsConstructor
public class VenueRepositoryQuerydslImpl implements VenueRepositoryQuerydsl{

    private final JPAQueryFactory queryFactory;
    @Override
    public Page<Venue> findVenueByKeyword(String keyword, Pageable pageable) {
        List<Venue> venues = queryFactory.selectFrom(venue)
                .where(venue.venueName.contains(keyword)
                        .or(venue.address.contains(keyword)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        System.out.println(venues.size());

        Long total = queryFactory.select(venue.count()).from(venue)
                .where(venue.venueName.contains(keyword)
                        .or(venue.address.contains(keyword)))
                .fetchFirst();

        return new PageImpl<>(venues, pageable, total);
    }
}
