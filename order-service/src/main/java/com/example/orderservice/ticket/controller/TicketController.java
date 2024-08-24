package com.example.orderservice.ticket.controller;

import com.example.orderservice.ticket.dto.TicketDetailResponse;
import com.example.orderservice.ticket.service.TicketService;
import com.example.servicecommon.auth.AllowedAuthority;
import com.example.servicecommon.auth.UserRole;
import com.example.servicecommon.dto.UserClaim;
import com.example.servicecommon.resolver.AuthPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @GetMapping("/bulk")
    @AllowedAuthority(UserRole.Authority.USER)
    public ResponseEntity<List<TicketDetailResponse>> findTickets(@AuthPrincipal UserClaim userClaim,
                                                                  @RequestParam List<Long> ticketIds) {
        return ResponseEntity.ok(ticketService.findTicket(userClaim.getUserId(), ticketIds));
    }
}
