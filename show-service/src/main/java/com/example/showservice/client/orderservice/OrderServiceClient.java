package com.example.showservice.client.orderservice;

import com.example.showservice.client.orderservice.dto.TicketCreateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "order-service")
public interface OrderServiceClient {

    @PostMapping("/internal/tickets")
    void createTicket(@RequestBody TicketCreateRequest ticketCreateRequest);
}
