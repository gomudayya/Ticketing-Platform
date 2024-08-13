package com.ticketland.ticketland.order.controller;

import com.ticketland.ticketland.order.dto.OrderDetailsResponse;
import com.ticketland.ticketland.order.dto.OrderPageResponse;
import com.ticketland.ticketland.order.dto.OrderPurchaseRequest;
import com.ticketland.ticketland.order.service.OrderService;
import com.ticketland.ticketland.user.constant.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<OrderDetailsResponse> orderTicket(@AuthenticationPrincipal Long userId, @RequestBody OrderPurchaseRequest orderPurchaseRequest) {
        return ResponseEntity.ok(orderService.orderTickets(userId, orderPurchaseRequest));
    }

    @Secured(UserRole.Authority.USER)
    @GetMapping
    public ResponseEntity<OrderPageResponse> findMyOrders(@AuthenticationPrincipal Long userId,
                                                          @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(orderService.findMyOrders(userId, pageable));
    }

    @Secured(UserRole.Authority.USER)
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetailsResponse> findOrderDetails(@AuthenticationPrincipal Long userId,
                                                                 @PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.findOrder(userId, orderId));
    }
}
