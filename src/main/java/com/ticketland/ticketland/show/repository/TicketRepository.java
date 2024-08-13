package com.ticketland.ticketland.show.repository;

import com.ticketland.ticketland.show.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, String> {
}
