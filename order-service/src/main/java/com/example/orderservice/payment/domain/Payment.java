package com.example.orderservice.payment.domain;

import com.example.orderservice.global.domain.BaseTimeEntity;
import com.example.orderservice.order.domain.Order;
import com.example.orderservice.payment.constant.PaymentStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Payment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    private Integer amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    protected Payment (){}
    @Builder
    public Payment(Order order, Integer amount, PaymentStatus paymentStatus) {
        this.order = order;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
    }
}
