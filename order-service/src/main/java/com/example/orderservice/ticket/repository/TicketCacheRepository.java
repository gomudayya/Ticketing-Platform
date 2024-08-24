package com.example.orderservice.ticket.repository;

import com.example.orderservice.ticket.domain.CachedTicket;
import org.springframework.data.repository.CrudRepository;

public interface TicketCacheRepository extends CrudRepository<CachedTicket, String> {
}
