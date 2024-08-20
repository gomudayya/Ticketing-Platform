package com.example.orderservice.ticket.controller;

import com.example.orderservice.ticket.dto.TicketCreateRequest;
import com.example.orderservice.ticket.service.TicketService;
import com.example.servicecommon.auth.AllowedAuthority;
import com.example.servicecommon.auth.UserRole;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping
//    @AllowedAuthority(UserRole.Authority.ADMIN)
    public ResponseEntity<?> createTickets(@RequestBody @Valid TicketCreateRequest ticketCreateRequest) {
        ticketService.generateTickets(ticketCreateRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public String test() {
        return "test";
    }
}
