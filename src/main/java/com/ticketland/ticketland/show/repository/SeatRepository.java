package com.ticketland.ticketland.show.repository;

import com.ticketland.ticketland.show.domain.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    List<Seat> findByVenueIdAndSection(Long venueId, String section);

    Optional<Seat> findBySectionAndNumber(String section, Integer number);
}
