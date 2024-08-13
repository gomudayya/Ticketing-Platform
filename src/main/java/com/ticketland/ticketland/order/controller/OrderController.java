package com.ticketland.ticketland.order.controller;

import com.ticketland.ticketland.order.dto.OrderPurchaseResponse;
import com.ticketland.ticketland.order.dto.OrderPurchaseRequest;
import com.ticketland.ticketland.order.service.OrderService;
import com.ticketland.ticketland.user.constant.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @Secured(UserRole.Authority.USER)
    @PostMapping
    public ResponseEntity<OrderPurchaseResponse> order(@AuthenticationPrincipal Long userId, @RequestBody OrderPurchaseRequest orderPurchaseRequest) {
        return ResponseEntity.ok(orderService.orderTickets(userId, orderPurchaseRequest));
    }
}
