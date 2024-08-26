package com.example.showservice.venue.repository;

import com.example.showservice.venue.domain.Venue;
import com.example.showservice.venue.repository.querydsl.VenueRepositoryQuerydsl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueRepository extends JpaRepository<Venue, Long>, VenueRepositoryQuerydsl {

}
