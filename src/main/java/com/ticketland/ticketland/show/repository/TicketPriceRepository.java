package com.ticketland.ticketland.show.repository;

import com.ticketland.ticketland.show.domain.TicketPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketPriceRepository extends JpaRepository<TicketPrice, Long> {
}
