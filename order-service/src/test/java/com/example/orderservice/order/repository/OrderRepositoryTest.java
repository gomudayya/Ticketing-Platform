package com.example.orderservice.order.repository;

import com.example.orderservice.fixture.order.OrderFixture;
import com.example.orderservice.fixture.ticket.TicketFixture;
import com.example.orderservice.order.constant.OrderStatus;
import com.example.orderservice.order.domain.Order;
import com.example.orderservice.testutil.JpaTest;
import com.example.orderservice.ticket.domain.Ticket;
import com.example.orderservice.ticket.repository.TicketRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static com.example.orderservice.fixture.ticket.TicketFixture.createSelectedTicket;
import static org.assertj.core.api.Assertions.assertThat;

class OrderRepositoryTest extends JpaTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    TicketRepository ticketRepository;

    @Nested
    @DisplayName("userId와 OrderStatus 를 이용한 주문 조회 테스트")
    class findByUserIdWithStatusFilter {

        @Test
        @DisplayName("주문 상태 필터 조건이 잘 작동하는지 테스트 한다.")
        void test1() {
            //given
            Long userId = 12L;
            Long showId = 23L;

            Ticket ticket1 = ticketRepository.save(createSelectedTicket(userId));
            Ticket ticket2 = ticketRepository.save(createSelectedTicket(userId));
            Ticket ticket3 = ticketRepository.save(createSelectedTicket(userId));

            Order pendingOrder = OrderFixture.createPendingOrder(userId, showId, ticket1);
            Order cancelOrder = OrderFixture.createCancelOrder(userId, showId, ticket2);
            Order successOrder = OrderFixture.createSuccessOrder(userId, showId, ticket3);

            orderRepository.save(pendingOrder);
            orderRepository.save(successOrder);
            orderRepository.save(cancelOrder);

            //when
            Page<Order> orderPage = orderRepository.findByUserIdWithStatusFilter(userId, PageRequest.of(0, 5),
                    OrderStatus.PENDING, OrderStatus.CANCEL);

            //then
            assertThat(orderPage.getContent().size()).isEqualTo(1);
            assertThat(orderPage.getContent().get(0)).isEqualTo(successOrder);
        }
    }
}