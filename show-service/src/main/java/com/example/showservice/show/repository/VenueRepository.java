package com.example.showservice.show.repository;

import com.example.showservice.show.domain.Venue;
import com.example.showservice.show.repository.querydsl.VenueRepositoryQuerydsl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueRepository extends JpaRepository<Venue, Long>, VenueRepositoryQuerydsl {

}
