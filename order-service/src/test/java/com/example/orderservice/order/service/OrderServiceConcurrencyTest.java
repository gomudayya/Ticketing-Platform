package com.example.orderservice.order.service;

import com.example.orderservice.client.showservice.ShowServiceClient;
import com.example.orderservice.order.dto.SeatDto;
import com.example.orderservice.order.exception.TicketUnavailableException;
import com.example.orderservice.order.repository.OrderRepository;
import com.example.orderservice.ticket.constant.TicketStatus;
import com.example.orderservice.ticket.domain.Ticket;
import com.example.orderservice.ticket.repository.TicketRepository;
import com.example.orderservice.ticket.repository.TicketStatusRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static com.example.orderservice.fixture.show.ShowSimpleResponseFixture.createTicketSaleActiveShow;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class OrderServiceConcurrencyTest {

    @MockBean
    ShowServiceClient showServiceClient;

    @Autowired
    @InjectMocks
    OrderService orderService;

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    TicketStatusRepository ticketStatusRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Nested
    @DisplayName("티켓 주문 동시성 테스트")
    class orderTickets {
        @AfterEach
        void cleanData() {
            redisTemplate.getConnectionFactory().getConnection().serverCommands().flushDb();
            orderRepository.deleteAll();
            ticketRepository.deleteAll();

            orderRepository.flush();
            ticketRepository.flush();

            List<Ticket> all = ticketRepository.findAll();
            System.out.println("AfterEach ------ all.size() = " + all.size());
        }

        @Test
        @DisplayName("(레디스 동시성 제어)  같은 티켓에 대한 주문이 여러개 왔을 때, 처리되는 주문은 가장 먼저온 주문이어야 하고 나머지는 예외가 발생해야 한다.")
        void test0() throws InterruptedException {
            //given
            Long showId = 3L;
            String seatSection = "B";
            Integer seatNumber = 221;
            String ticketCode = Ticket.makeCode(showId, seatSection, seatNumber);

            Ticket ticket = Ticket.builder()
                    .price(12345)
                    .code(ticketCode)
                    .build();

            ticketRepository.save(ticket);
            ticketStatusRepository.saveAll(showId, Map.of(ticketCode, TicketStatus.AVAILABLE.name())); // Redis에 상태저장

            int threadCount = 25;
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
                    } catch (TicketUnavailableException e) { //
                        failedReservations.incrementAndGet();
                    } finally {
                        latch.countDown();
                    }
                });
            }
            latch.await();

            Map<String, String> map2 = ticketStatusRepository.findAll(showId);
            System.out.println(map2);
            //then
            assertThat(successfulReservations.get()).isEqualTo(1);
            assertThat(failedReservations.get()).isEqualTo(threadCount - 1);
        }

        @Test
        @DisplayName("(DB 비관적락) 같은 티켓에 대한 주문이 여러개 왔을 때, 처리되는 주문은 가장 먼저온 주문이어야 하고 나머지는 예외가 발생해야 한다.")
        void test1() throws InterruptedException {
            List<Ticket> tickets = ticketRepository.findAll();
            System.out.println("tickets.size() = " + tickets.size());

            // 하 대체 왜그러는거임?????????????????????????????????????????????????????????????????????????????????????????????
            /////////////////////////////////////////////




            //given
            Long showId = 4L;
            String seatSection = "B";
            Integer seatNumber = 221;
            String ticketCode = Ticket.makeCode(showId, seatSection, seatNumber);

            Ticket ticket = Ticket.builder()
                    .price(12345)
                    .code(ticketCode)
                    .build();

            if (ticketRepository.findByCode(ticketCode).isPresent()) {
                System.out.println("이미 존재해용");
            }
            
            ticketRepository.save(ticket);
            int threadCount = 25;

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
    }
}