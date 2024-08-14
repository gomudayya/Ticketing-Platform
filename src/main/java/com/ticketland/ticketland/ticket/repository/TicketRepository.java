package com.ticketland.ticketland.ticket.repository;

import com.ticketland.ticketland.ticket.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, String> {
}
