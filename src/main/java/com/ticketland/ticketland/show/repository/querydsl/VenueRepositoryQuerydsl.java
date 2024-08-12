package com.ticketland.ticketland.show.repository.querydsl;

import com.ticketland.ticketland.show.domain.Venue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VenueRepositoryQuerydsl {
    Page<Venue> findVenueByKeyword(String keyword, Pageable pageable);
}
