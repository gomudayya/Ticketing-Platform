package com.ticketland.ticketland.show.repository;

import com.ticketland.ticketland.show.domain.Venue;
import com.ticketland.ticketland.show.repository.querydsl.VenueRepositoryQuerydsl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueRepository extends JpaRepository<Venue, Long>, VenueRepositoryQuerydsl {

}
