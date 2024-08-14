package com.ticketland.ticketland.order.dto;

import com.ticketland.ticketland.order.domain.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderSimpleResponse {
    private Long orderId;
    private Integer totalPrice;
    private Integer quantity;
    private String orderStatus;
    private String showTitle;

    public static OrderSimpleResponse from(Order order) {
        return builder()
                .orderId(order.getId())
                .totalPrice(order.getTotalPrice())
                .quantity(order.getQuantity())
                .orderStatus(order.getOrderStatus().getDescription())
                .showTitle(order.getShow().getTitle())
                .build();
    }
}
