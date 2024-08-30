package com.example.orderservice.order.service;

import com.example.orderservice.client.showservice.ShowServiceClient;
import com.example.orderservice.order.dto.SeatDto;
import com.example.orderservice.order.exception.TicketUnavailableException;
import com.example.orderservice.order.exception.TicketSaleNotActiveException;
import com.example.orderservice.ticket.constant.TicketStatus;
import com.example.orderservice.ticket.domain.Ticket;
import com.example.orderservice.ticket.repository.TicketRepository;
import com.example.orderservice.ticket.repository.TicketStatusRepository;
import com.example.orderservice.ticket.service.TicketCacheService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static com.example.orderservice.fixture.show.ShowSimpleResponseFixture.createBeforeTicketOpenShow;
import static com.example.orderservice.fixture.show.ShowSimpleResponseFixture.createTicketSaleActiveShow;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class OrderServiceTest {

    @MockBean
    private ShowServiceClient showServiceClient;

    @Autowired
    @InjectMocks
    OrderService orderService;

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    TicketStatusRepository ticketStatusRepository;

    @Nested
    @DisplayName("티켓 주문 테스트")
    class orderTickets {

        @Test
        @DisplayName("(레디스 동시성 제어)  같은 티켓에 대한 주문이 여러개 왔을 때, 처리되는 주문은 가장 먼저온 주문이어야 하고 나머지는 예외가 발생해야 한다.")
        void test0() throws InterruptedException {
            //given
            Long showId = 3L;
            String seatSection = "B";
            Integer seatNumber = 221;
            String ticketCode = Ticket.makeCode(showId, seatSection, seatNumber);

            Ticket ticket = Ticket.builder()
                    .code(ticketCode)
                    .price(66000)
                    .build();

            ticketRepository.save(ticket);
            Map<String , String> ticketStatuses = new HashMap<>();
            ticketStatuses.put(ticket.getCode(), TicketStatus.AVAILABLE.name());
            ticketStatusRepository.save(showId, ticketStatuses); // Redis에 상태저장

            int threadCount = 50;
            ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
            CountDownLatch latch = new CountDownLatch(threadCount);
            AtomicInteger successfulReservations = new AtomicInteger(0);
            AtomicInteger failedReservations = new AtomicInteger(0);

            given(showServiceClient.getShow(showId)).willReturn(createTicketSaleActiveShow());

            //when
            for (long userId = 1; userId <= threadCount; userId++) {
                long finalUserId = userId; // 람다는 순수함수 조건을 만족해야 하기 때문에 값을 변경할 수 없음
                executorService.execute(() -> {
                    try {
                        orderService.orderTickets(finalUserId, showId, List.of(new SeatDto(seatSection, seatNumber))); // 여기서 예외 발생
                        successfulReservations.incrementAndGet();
                    } catch (TicketUnavailableException e) {
                        failedReservations.incrementAndGet();
                    } finally {
                        latch.countDown();
                    }
                });
            }

            latch.await();

            //then
            assertThat(successfulReservations.get()).isEqualTo(1);
            assertThat(failedReservations.get()).isEqualTo(threadCount - 1);
        }

        @Test
        @DisplayName("(DB 비관적락) 같은 티켓에 대한 주문이 여러개 왔을 때, 처리되는 주문은 가장 먼저온 주문이어야 하고 나머지는 예외가 발생해야 한다.")
        void test1() throws InterruptedException {
            //given
            Long showId = 5L;
            String seatSection = "B";
            Integer seatNumber = 221;
            String ticketCode = Ticket.makeCode(showId, seatSection, seatNumber);

            Ticket ticket = Ticket.builder()
                    .code(ticketCode)
                    .price(66000)
                    .build();

            ticketRepository.save(ticket);

            int threadCount = 50;
            ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
            CountDownLatch latch = new CountDownLatch(threadCount);
            AtomicInteger successfulReservations = new AtomicInteger(0);
            AtomicInteger failedReservations = new AtomicInteger(0);

            given(showServiceClient.getShow(showId)).willReturn(createTicketSaleActiveShow());

            //when
            for (long userId = 1; userId <= threadCount; userId++) {
                long finalUserId = userId; // 람다는 순수함수 조건을 만족해야 하기 때문에 값을 변경할 수 없음
                executorService.execute(() -> {
                    try {
                        orderService.orderTickets(finalUserId, showId, List.of(new SeatDto(seatSection, seatNumber))); // 여기서 예외 발생
                        successfulReservations.incrementAndGet();
                    } catch (TicketUnavailableException e) {
                        failedReservations.incrementAndGet();
                    } finally {
                        latch.countDown();
                    }
                });
            }

            latch.await();

            //then
            assertThat(successfulReservations.get()).isEqualTo(1);
            assertThat(failedReservations.get()).isEqualTo(threadCount - 1);
        }

        @Test
        @DisplayName("티켓 판매 시간이 활성화 되지않은 주문은 예외가 발생해야 한다.")
        void test2() {
            //given
            given(showServiceClient.getShow(any())).willReturn(createBeforeTicketOpenShow());

            //when, then
            assertThatThrownBy(() -> orderService.orderTickets(123L, 123L, null))
                    .isInstanceOf(TicketSaleNotActiveException.class);
        }
    }
}