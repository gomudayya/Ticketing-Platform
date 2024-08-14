package com.ticketland.ticketland.order.dto;


import com.ticketland.ticketland.order.domain.Order;
import com.ticketland.ticketland.ticket.dto.TicketResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailsResponse {

    private Long orderId;
    private Integer totalPrice; // 전체 가격
    private String orderStatus; // 주문 상태
    private Integer quantity; // 수량
    private List<TicketResponse> tickets; // 티켓 세부 정보 리스트

    public static OrderDetailsResponse from(Order order) {
        List<TicketResponse> ticketResponses = order.getTickets().stream()
                .map(TicketResponse::from)
                .toList();

        return builder()
                .orderId(order.getId())
                .totalPrice(order.getTotalPrice())
                .orderStatus(order.getOrderStatus().getDescription())
                .quantity(order.getQuantity())
                .tickets(ticketResponses)
                .build();
    }
}
