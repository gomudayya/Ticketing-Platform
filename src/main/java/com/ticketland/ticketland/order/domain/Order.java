package com.ticketland.ticketland.order.domain;

import com.ticketland.ticketland.global.domain.BaseTimeEntity;
import com.ticketland.ticketland.order.constant.OrderStatus;
import com.ticketland.ticketland.show.domain.Show;
import com.ticketland.ticketland.ticket.domain.Ticket;
import com.ticketland.ticketland.user.domain.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "show_id")
    private Show show;

    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST)
    private List<OrderTicket> orderTickets = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    protected Order() {
    }

    private Order(User user, Show show, OrderStatus orderStatus) {
        this.user = user;
        this.show = show;
        this.orderStatus = orderStatus;
    }

    public static Order createOrder(User user, Show show, List<OrderTicket> orderTickets) {
        Order order = new Order(user, show, OrderStatus.ORDER);
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
        return user.getId().equals(userId);
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

        for (OrderTicket orderTicket : orderTickets) {
            orderTicket.cancel();
        }
    }
}
