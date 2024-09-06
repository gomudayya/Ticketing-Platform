package com.example.orderservice.fixture.order;

import com.example.orderservice.order.domain.Order;
import com.example.orderservice.order.domain.OrderTicket;
import com.example.orderservice.ticket.domain.Ticket;

import java.util.List;

public class OrderFixture {

    public static Order createPendingOrder(Long userId, Long showId, Ticket ticket) {
        OrderTicket orderTicket = OrderTicket.createOrderTicket(ticket);
        return Order.createOrder(userId, showId, List.of(orderTicket));
    }

    public static Order createCancelOrder(Long userId, Long showId, Ticket ticket) {
        Order order = createPendingOrder(userId, showId, ticket);
        order.cancel();

        return order;
    }

    public static Order createSuccessOrder(Long userId, Long showId, Ticket ticket) {
        Order order = createPendingOrder(userId, showId, ticket);
        order.successBy(userId);

        return order;
    }

}
