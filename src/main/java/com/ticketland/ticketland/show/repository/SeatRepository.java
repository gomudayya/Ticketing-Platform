package com.ticketland.ticketland.show.repository;

import com.ticketland.ticketland.show.domain.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Long> {
}
