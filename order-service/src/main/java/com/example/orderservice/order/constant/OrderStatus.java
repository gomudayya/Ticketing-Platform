package com.example.orderservice.order.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {

    PENDING("주문대기"),
    SUCCESS("주문완료"),
    CANCEL("주문취소"), // 결제 도중 취소
    REFUND("주문환불");

    private final String description;
}
