package com.example.showservice.venue.repository;

import com.example.showservice.venue.domain.Seat;
import com.example.showservice.venue.repository.querydsl.SeatRepositoryQuerydsl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long>, SeatRepositoryQuerydsl {

    List<Seat> findByVenueIdAndSection(Long venueId, String section);

    Optional<Seat> findBySectionAndNumber(String section, Integer number);

}
