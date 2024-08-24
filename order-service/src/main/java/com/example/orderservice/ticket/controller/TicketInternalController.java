package com.example.orderservice.ticket.controller;

import com.example.orderservice.ticket.dto.TicketCreateRequest;
import com.example.orderservice.ticket.service.TicketCacheService;
import com.example.orderservice.ticket.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/tickets")
@RequiredArgsConstructor
public class TicketInternalController {

    private final TicketService ticketService;
    private final TicketCacheService ticketCacheService;

    @PostMapping
    public ResponseEntity<Void> createTickets(@RequestBody @Valid TicketCreateRequest ticketCreateRequest) {
        ticketService.generateTickets(ticketCreateRequest);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/show/{showId}/cache")
    public ResponseEntity<Void> cacheTickets(@PathVariable Long showId) {
        ticketCacheService.cacheTickets(showId);
        return ResponseEntity.noContent().build();
    }
}
