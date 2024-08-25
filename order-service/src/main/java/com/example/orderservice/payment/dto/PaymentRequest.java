package com.example.orderservice.payment.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor

public class PaymentRequest {
    private Long orderId;
    private Integer amount;
}
