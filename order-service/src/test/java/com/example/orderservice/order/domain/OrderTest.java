package com.example.orderservice.order.domain;

import com.example.orderservice.order.constant.OrderStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

class OrderTest {

    @Test
    @DisplayName("Order 를 처음에 생성할 때는 항상 Pending 상태여야 한다.")
    void test1() {
        //given
        Long userId = 31L;
        Long showId = 44L;
        OrderTicket orderTicket = Mockito.mock(OrderTicket.class);

        //when
        Order order = Order.createOrder(userId, showId, List.of(orderTicket));

        //then
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.PENDING);
    }

    @Test
    @DisplayName("전체 주문 가격 테스트")
    void test2() {
        //given
        int priceA = 13000;
        int priceB = 30000;
        int priceC = 44110;
        OrderTicket orderTicketA = Mockito.mock(OrderTicket.class);
        OrderTicket orderTicketB = Mockito.mock(OrderTicket.class);
        OrderTicket orderTicketC = Mockito.mock(OrderTicket.class);

        given(orderTicketA.getPrice()).willReturn(priceA);
        given(orderTicketB.getPrice()).willReturn(priceB);
        given(orderTicketC.getPrice()).willReturn(priceC);

        //when
        Order order = Order.createOrder(31L, 24L, List.of(orderTicketA, orderTicketB, orderTicketC));
        Integer totalPrice = order.getTotalPrice();

        //then
        assertThat(totalPrice).isEqualTo(priceA + priceB + priceC);
    }




}