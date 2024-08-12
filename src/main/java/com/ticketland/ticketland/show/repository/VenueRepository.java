package com.ticketland.ticketland.show.repository;

import com.ticketland.ticketland.show.domain.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueRepository extends JpaRepository<Venue, Long> {
}
