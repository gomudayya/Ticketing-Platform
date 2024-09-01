package com.example.orderservice.order.service;

import com.example.orderservice.client.showservice.ShowServiceClient;
import com.example.orderservice.order.exception.TicketSaleNotActiveException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.orderservice.fixture.show.ShowSimpleResponseFixture.createBeforeTicketOpenShow;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @InjectMocks
    OrderService orderService;
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

}
