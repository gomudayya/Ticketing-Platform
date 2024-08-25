package com.example.orderservice.payment.service;

import com.example.orderservice.order.domain.Order;
import com.example.orderservice.order.service.OrderService;
import com.example.orderservice.payment.domain.Payment;
import com.example.orderservice.payment.dto.PaymentRequest;
import com.example.orderservice.payment.exception.InvalidPaymentException;
import com.example.orderservice.payment.repository.PaymentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    OrderService orderService;
    @Mock
    PaymentRepository paymentRepository;
    @InjectMocks
    PaymentService paymentService;

    @Nested
    @DisplayName("결제 성공 테스트")
    class successPayment {

        @Test
        @DisplayName("결제 요청으로 들어온 금액과, 주문서의 합계 금액이 다르다면 예외가 발생하고, 주문이 취소된다.")
        void test1() {
            //given
            Long userId = 5L;
            Long orderId = 21L;
            int payAmount = 220_000;
            PaymentRequest paymentRequest = new PaymentRequest(orderId, payAmount);
            Order order = mock(Order.class);

            given(orderService.findOrder(userId, orderId)).willReturn(order);
            given(order.getTotalPrice()).willReturn(payAmount - 10_000);

            //when, then
            assertThatThrownBy(() -> paymentService.successPayment(userId, paymentRequest))
                    .isInstanceOf(InvalidPaymentException.class);
            verify(order, times(1)).cancel();
        }

        @Test
        @DisplayName("결제 요청으로 들어온 금액과, 주문서의 합계 금액이 같으면 주문이 지불된다.")
        void test2() {
            //given
            Long userId = 5L;
            Long orderId = 21L;
            int payAmount = 220_000;
            PaymentRequest paymentRequest = new PaymentRequest(orderId, payAmount);
            Order order = mock(Order.class);

            given(orderService.findOrder(userId, orderId)).willReturn(order);
            given(order.getTotalPrice()).willReturn(payAmount);

            //when
            paymentService.successPayment(userId, paymentRequest);

            //then
            verify(order).isPaidBy(userId);  // times(1)과 동일
            verify(paymentRepository, times(1)).save(any(Payment.class));
        }
    }

    @Nested
    @DisplayName("결제 취소 테스트")
    class failPayment {

        @Test
        @DisplayName("결제 취소요청이 들어오면 주문도 취소되어야한다.")
        void test1() {
            //given
            Long userId = 5L;
            Long orderId = 21L;
            int payAmount = 220_000;
            PaymentRequest paymentRequest = new PaymentRequest(orderId, payAmount);
            Order order = mock(Order.class);

            given(orderService.findOrder(userId, orderId)).willReturn(order);

            //when
            paymentService.failPayment(userId, paymentRequest);

            //then
            verify(order).cancel();  // times(1)과 동일
        }
    }
}