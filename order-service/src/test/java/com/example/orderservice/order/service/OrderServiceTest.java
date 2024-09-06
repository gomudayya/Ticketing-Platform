package com.example.orderservice.order.service;

import com.example.orderservice.client.showservice.ShowServiceClient;
import com.example.orderservice.order.constant.OrderStatus;
import com.example.orderservice.order.domain.Order;
import com.example.orderservice.order.exception.InvalidRefundException;
import com.example.orderservice.order.exception.TicketSaleNotActiveException;
import com.example.orderservice.order.repository.OrderRepository;
import com.example.servicecommon.exception.CustomAccessDeniedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.example.orderservice.fixture.show.ShowSimpleResponseFixture.createBeforeTicketOpenShow;
import static com.example.orderservice.fixture.show.ShowSimpleResponseFixture.createTicketSaleEndedShow;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @InjectMocks
    OrderService orderService;

    @Mock
    OrderRepository orderRepository;

    @Mock
    ShowServiceClient showServiceClient;



    @Nested
    @DisplayName("티켓 주문 테스트")
    class orderTickets {
        @Test
        @DisplayName("티켓 판매 시간이 활성화 되지않은 주문은 예외가 발생해야 한다.")
        void test1() {
            //given
            given(showServiceClient.getShow(any())).willReturn(createBeforeTicketOpenShow());

            //when, then
            assertThatThrownBy(() -> orderService.orderTickets(123L, 123L, null))
                    .isInstanceOf(TicketSaleNotActiveException.class);
        }
    }

    @Nested
    @DisplayName("주문 환불 테스트")
    class refundOrder {

        @Test
        @DisplayName("티켓 판매 활성화 시간 이후에 들어온 환불요청은 예외가 발생해야 한다.")
        void test1() {
            //given
            Long userId = 123L;
            Long orderId = 123L;

            Order order = mock(Order.class);
            given(order.isOwnedBy(userId)).willReturn(true);
            given(orderRepository.findById(orderId)).willReturn(Optional.of(order));
            given(showServiceClient.getShow(any())).willReturn(createTicketSaleEndedShow());

            //when, then
            assertThatThrownBy(() -> orderService.refundOrder(userId, orderId))
                    .isInstanceOf(TicketSaleNotActiveException.class);
        }

        @Test
        @DisplayName("주문 완료되지 않은 상품에 대한 환불 요청이 들어오면 예외가 발생해야 한다.")
        void test2() {
            //given
            Long userId = 22L;
            Long orderId = 1131L;


            //when, then
            assertThatThrownBy(() -> orderService.refundOrder(userId, orderId))
                    .isInstanceOf(InvalidRefundException.class);

        }
    }

    @Test
    @DisplayName("(소유권 체크) 주문을 가져올 때, Order의 userId와 파라미터의 userId가 일치하지 않으면 예외가 발생한다.")
    void test1512() {
        //given
        Long orderId = 1561L;
        Long userId = 1124L;
        Order order = mock(Order.class);

        given(order.isOwnedBy(userId)).willReturn(false);
        given(orderRepository.findById(any())).willReturn(Optional.of(order));

        //when, then
        assertThatThrownBy(() -> orderService.findOrder(userId, orderId))
                .isInstanceOf(CustomAccessDeniedException.class);
    }

    @Nested
    @DisplayName("주문 상세조회 테스트")
    class readOrder {
        @Test
        @DisplayName("주문 상태가 PENDING 상태에 대한 주문에 대해 조회를 요청할 시 예외가 발생해야 한다.")
        void test12512() {
            //given
            Long orderId = 1412L;
            Long userId = 132L;

            Order order = mock(Order.class);
            given(orderRepository.findById(any())).willReturn(Optional.of(order));
            given(order.isOwnedBy(userId)).willReturn(true);
            given(order.getOrderStatus()).willReturn(OrderStatus.PENDING);

            //when, then
            assertThatThrownBy(() -> orderService.findMyOrder(userId, orderId))
                    .isInstanceOf(CustomAccessDeniedException.class);
        }

        @Test
        @DisplayName("주문 상태가 CANCEL 상태에 대한 주문에 대해 조회를 요청할 시 예외가 발생해야 한다.")
        void test143() {
            //given
            Long orderId = 1412L;
            Long userId = 132L;

            Order order = mock(Order.class);
            given(orderRepository.findById(any())).willReturn(Optional.of(order));
            given(order.isOwnedBy(userId)).willReturn(true);
            given(order.getOrderStatus()).willReturn(OrderStatus.CANCEL);

            //when, then
            assertThatThrownBy(() -> orderService.findMyOrder(userId, orderId))
                    .isInstanceOf(CustomAccessDeniedException.class);
        }
    }
}
