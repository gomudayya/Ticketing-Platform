package com.example.showservice.venue.repository.querydsl;

import com.example.showservice.venue.domain.Venue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VenueRepositoryQuerydsl {
    Page<Venue> findVenueByKeyword(String keyword, Pageable pageable);
}
