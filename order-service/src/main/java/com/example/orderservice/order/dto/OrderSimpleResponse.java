package com.example.orderservice.order.dto;

import com.example.orderservice.client.showservice.dto.ShowSimpleResponse;
import com.example.orderservice.order.domain.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    private String showTitleImage;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    public static OrderSimpleResponse from(Order order, ShowSimpleResponse show) {
        return builder()
                .orderId(order.getId())
                .totalPrice(order.getTotalPrice())
                .quantity(order.getQuantity())
                .orderStatus(order.getOrderStatus().getDescription())
                .showTitle(show.getTitle())
                .showTitleImage(show.getTitleImage())
                .createdTime(order.getCreatedTime())
                .updatedTime(order.getUpdatedTime())
                .build();
    }
}
