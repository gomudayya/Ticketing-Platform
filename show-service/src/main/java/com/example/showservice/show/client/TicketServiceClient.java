package com.example.showservice.show.client;

import com.example.showservice.show.dto.TicketCreateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "order-service")
public interface TicketServiceClient {

    @PostMapping("/api/tickets")
    void createTicket(@RequestBody TicketCreateRequest ticketCreateRequest);

    @GetMapping("/api/tickets")
    String test();
}
