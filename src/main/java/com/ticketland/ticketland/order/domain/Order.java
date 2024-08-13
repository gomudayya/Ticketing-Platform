package com.ticketland.ticketland.order.domain;

import com.ticketland.ticketland.global.domain.BaseTimeEntity;
import com.ticketland.ticketland.order.constant.OrderStatus;
import com.ticketland.ticketland.show.domain.Ticket;
import com.ticketland.ticketland.user.domain.User;
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

    @OneToMany(mappedBy = "order")
    private List<Ticket> tickets = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    protected Order() {
    }

    private Order(User user, OrderStatus orderStatus) {
        this.user = user;
        this.orderStatus = orderStatus;
    }

    public static Order of(User user, OrderStatus orderStatus) {
        return new Order(user, orderStatus);
    }


    public Integer getTotalPrice() {
        return tickets.stream()
                .mapToInt(Ticket::getPrice)
                .sum();
    }
}
