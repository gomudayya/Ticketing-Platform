package com.example.orderservice.order.domain;

import com.example.orderservice.fixture.ticket.TicketFixture;
import com.example.orderservice.order.constant.OrderStatus;
import com.example.orderservice.ticket.constant.TicketStatus;
import com.example.orderservice.ticket.domain.Ticket;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class OrderTest {

    @Test
    @DisplayName("Order 를 처음에 생성할 때는 항상 Pending 상태여야 한다.")
    void test1() {
        //given
        Long userId = 31L;
        Long showId = 44L;
        OrderTicket orderTicket = mock(OrderTicket.class);

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
        OrderTicket orderTicketA = mock(OrderTicket.class);
        OrderTicket orderTicketB = mock(OrderTicket.class);
        OrderTicket orderTicketC = mock(OrderTicket.class);

        given(orderTicketA.getPrice()).willReturn(priceA);
        given(orderTicketB.getPrice()).willReturn(priceB);
        given(orderTicketC.getPrice()).willReturn(priceC);

        //when
        Order order = Order.createOrder(31L, 24L, List.of(orderTicketA, orderTicketB, orderTicketC));
        Integer totalPrice = order.getTotalPrice();

        //then
        assertThat(totalPrice).isEqualTo(priceA + priceB + priceC);
    }

    @Test
    @DisplayName("주문 취소 테스트")
    void test3() {
        //given
        Long userId = 123L;
        Ticket ticket1 = TicketFixture.createSelectedTicket(userId);
        Ticket ticket2 = TicketFixture.createSelectedTicket(userId);;

        OrderTicket orderTicket1 = OrderTicket.createOrderTicket(ticket1);
        OrderTicket orderTicket2 = OrderTicket.createOrderTicket(ticket2);

        Order order = Order.createOrder(123L, 123L, List.of(orderTicket1, orderTicket2));
        //when
        order.cancel();

        //then
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.CANCEL);
        assertThat(ticket1.getTicketStatus()).isEqualTo(TicketStatus.AVAILABLE);
        assertThat(ticket2.getTicketStatus()).isEqualTo(TicketStatus.AVAILABLE);
        assertThat(ticket1.getUserId()).isEqualTo(null);
        assertThat(ticket2.getUserId()).isEqualTo(null);
    }

    @Test
    @DisplayName("주문 성공 테스트")
    void test4() {
        //given
        Long userId = 123L;
        Long showId = 123L;
        Ticket ticket1 = TicketFixture.createSelectedTicket(userId);
        Ticket ticket2 = TicketFixture.createSelectedTicket(userId);;

        OrderTicket orderTicket1 = OrderTicket.createOrderTicket(ticket1);
        OrderTicket orderTicket2 = OrderTicket.createOrderTicket(ticket2);

        Order order = Order.createOrder(userId, showId, List.of(orderTicket1, orderTicket2));
        //when
        order.successBy(userId);

        //then
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.SUCCESS);
        assertThat(ticket1.getTicketStatus()).isEqualTo(TicketStatus.SOLD);
        assertThat(ticket2.getTicketStatus()).isEqualTo(TicketStatus.SOLD);
    }

    @Test
    @DisplayName("주문 환불 테스트")
    void test5() {
        //given
        Long userId = 123L;
        Ticket ticket1 = TicketFixture.createSelectedTicket(userId);
        Ticket ticket2 = TicketFixture.createSelectedTicket(userId);;

        OrderTicket orderTicket1 = OrderTicket.createOrderTicket(ticket1);
        OrderTicket orderTicket2 = OrderTicket.createOrderTicket(ticket2);

        Order order = Order.createOrder(userId, 123L, List.of(orderTicket1, orderTicket2));
        order.successBy(123L);
        //when
        order.refund();

        //then
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.REFUND);
        assertThat(ticket1.getTicketStatus()).isEqualTo(TicketStatus.AVAILABLE);
        assertThat(ticket2.getTicketStatus()).isEqualTo(TicketStatus.AVAILABLE);
        assertThat(ticket1.getUserId()).isEqualTo(null);
        assertThat(ticket2.getUserId()).isEqualTo(null);
    }
}