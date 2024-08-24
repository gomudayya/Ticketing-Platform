package com.example.orderservice.order.controller;

import com.example.orderservice.order.service.OrderService;
import com.example.orderservice.order.dto.OrderDetailsResponse;
import com.example.orderservice.order.dto.OrderPageResponse;
import com.example.orderservice.order.dto.OrderPurchaseRequest;
import com.example.servicecommon.auth.AllowedAuthority;
import com.example.servicecommon.auth.UserRole;
import com.example.servicecommon.dto.UserClaim;
import com.example.servicecommon.resolver.AuthPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    @AllowedAuthority(UserRole.Authority.USER)
    public ResponseEntity<OrderDetailsResponse> orderTicket(@AuthPrincipal UserClaim userClaim, @RequestBody OrderPurchaseRequest orderPurchaseRequest) {
        OrderDetailsResponse orderDetailsResponse
                = orderService.orderTickets(userClaim.getUserId(), orderPurchaseRequest.getShowId(), orderPurchaseRequest.getSeats());

        return ResponseEntity.ok(orderDetailsResponse);
    }

    @GetMapping
    @AllowedAuthority(UserRole.Authority.USER)
    public ResponseEntity<OrderPageResponse> findMyOrders(@AuthPrincipal UserClaim userClaim,
                                                          @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(orderService.findMyOrders(userClaim.getUserId(), pageable));
    }

    @GetMapping("/{orderId}")
    @AllowedAuthority(UserRole.Authority.USER)
    public ResponseEntity<OrderDetailsResponse> findOrderDetails(@AuthPrincipal UserClaim userClaim,
                                                                 @PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.readOrder(userClaim.getUserId(), orderId));
    }

    @PostMapping("/{orderId}/refund")
    @AllowedAuthority(UserRole.Authority.USER)
    public ResponseEntity<OrderDetailsResponse> refundOrder(@AuthPrincipal UserClaim userClaim,
                                                            @PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.refundOrder(userClaim.getUserId(), orderId));
    }
}
