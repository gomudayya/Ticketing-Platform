package com.example.orderservice.order.domain;

import com.example.orderservice.global.domain.BaseTimeEntity;
import com.example.orderservice.ticket.domain.Ticket;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class OrderTicket extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    public int getPrice() {
        return ticket.getPrice();
    }

    protected OrderTicket() {}

    private OrderTicket(Ticket ticket) {
        this.ticket = ticket;
    }
    public static OrderTicket createOrderTicket(Ticket ticket) {
        return new OrderTicket(ticket);
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void cancel() {
        ticket.beRefunded();
    }
}
