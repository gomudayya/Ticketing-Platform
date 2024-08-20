package com.example.orderservice.order.dto;


import com.example.orderservice.client.showservice.dto.ShowSimpleResponse;
import com.example.orderservice.order.domain.Order;
import com.example.orderservice.ticket.dto.TicketResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailsResponse {

    private Long orderId;
    private ShowSimpleResponse show;
    private Integer totalPrice; // 전체 가격
    private String orderStatus; // 주문 상태
    private Integer quantity; // 수량
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private List<TicketResponse> tickets; // 티켓 세부 정보 리스트

    public static OrderDetailsResponse from(Order order, ShowSimpleResponse showSimpleResponse) {
        List<TicketResponse> ticketResponses = order.getTickets().stream()
                .map(TicketResponse::from)
                .toList();

        return builder()
                .show(showSimpleResponse)
                .orderId(order.getId())
                .totalPrice(order.getTotalPrice())
                .orderStatus(order.getOrderStatus().getDescription())
                .quantity(order.getQuantity())
                .createdTime(order.getCreatedTime())
                .updatedTime(order.getUpdatedTime())
                .tickets(ticketResponses)
                .build();
    }
}
