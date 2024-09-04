package com.example.showservice.show.repository;

import com.example.showservice.show.domain.Show;
import com.example.showservice.show.repository.querydsl.ShowRepositoryQuerydsl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ShowRepository extends JpaRepository<Show, Long>, ShowRepositoryQuerydsl {
    List<Show> findByIdIn(List<Long> showIds);

    List<Show> findShowByTicketOpenTimeBetween(LocalDateTime now, LocalDateTime next24Hours);
}
