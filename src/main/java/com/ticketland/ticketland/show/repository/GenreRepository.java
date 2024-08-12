package com.ticketland.ticketland.show.repository;

import com.ticketland.ticketland.show.domain.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
