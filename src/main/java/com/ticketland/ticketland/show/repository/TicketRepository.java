package com.ticketland.ticketland.show.repository;

import com.ticketland.ticketland.show.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
