package com.example.orderservice.order.domain;

import com.example.orderservice.global.domain.BaseTimeEntity;
import com.example.orderservice.order.constant.OrderStatus;
import com.example.orderservice.ticket.domain.Ticket;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Table(name = "orders")
@Entity
@Getter
public class Order extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long showId;

    @OneToMany(mappedBy = "order", cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    private List<OrderTicket> orderTickets = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    protected Order() {
    }

    private Order(Long userId, Long showId, OrderStatus orderStatus) {
        this.userId = userId;
        this.showId = showId;
        this.orderStatus = orderStatus;
    }

    public static Order createOrder(Long userId, Long showId, List<OrderTicket> orderTickets) {
        Order order = new Order(userId, showId, OrderStatus.PENDING);
        orderTickets.forEach(order::addOrderTicket);
        return order;
    }

    public Integer getTotalPrice() {
        return orderTickets.stream()
                .mapToInt(OrderTicket::getPrice)
                .sum();
    }

    public Integer getQuantity() {
        return orderTickets.size();
    }


    public boolean isOwnedBy(Long userId) {
        return this.userId.equals(userId);
    }

    public void addOrderTicket(OrderTicket orderTicket) {
        orderTickets.add(orderTicket);
        orderTicket.setOrder(this); // 연관관계 편의 로직
    }

    public List<Ticket> getTickets() {
        return orderTickets.stream()
                .map(OrderTicket::getTicket)
                .toList();
    }

    public void cancel() {
        orderStatus = OrderStatus.CANCEL;
        orderTickets.forEach(OrderTicket::cancel);
    }

    public void refund() {
        orderStatus = OrderStatus.REFUND;
        orderTickets.forEach(OrderTicket::makeAvailable);
    }

    public void successBy(Long userId) {
        orderStatus = OrderStatus.SUCCESS;
        orderTickets.forEach(orderTicket -> orderTicket.isPaidBy(userId));
    }
}
