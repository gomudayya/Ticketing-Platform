package com.example.showservice.show.service;

import com.example.showservice.generator.ShowGenerator;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.showservice.show.service.ShowSchedulerService.TICKET_OPEN_LOOKAHEAD_HOURS;
import static com.example.showservice.show.service.ShowSchedulerService.UPCOMING_TICKET_OPEN_TOPIC;

@SpringBootTest
@EmbeddedKafka
class ShowSchedulerServiceTest {

    @Autowired
    ShowSchedulerService showSchedulerService;

    @Autowired
    ShowGenerator showGenerator;

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    ConsumerFactory<String, Object> consumerFactory;

    @Nested
    @DisplayName("cacheBeforeTicketOpenTime() 테스트")
    class cacheBeforeTicketOpenTime {
        @Test
        @DisplayName("현재 시간으로부터 'TICKET_OPEN_LOOKAHEAD_HOURS' 이내에 티켓 오픈이 시작되는 공연들이 메시지로 들어가야 한다.")
        void cacheBeforeTicketOpenTime() {
            //given
            Consumer<String, Object> consumer = consumerFactory.createConsumer();
            consumer.subscribe(List.of(UPCOMING_TICKET_OPEN_TOPIC));

            LocalDateTime withinTime = LocalDateTime.now().plusHours(TICKET_OPEN_LOOKAHEAD_HOURS - 1);
            LocalDateTime outsideTime = LocalDateTime.now().plusHours(TICKET_OPEN_LOOKAHEAD_HOURS + 1);

            showGenerator.saveShows(withinTime, 4);
            showGenerator.saveShows(outsideTime, 7);
            //when
            showSchedulerService.cacheShowUpcomingTicketOpenTime();

            //then
            ConsumerRecords<String, Object> consumerRecords = consumer.poll(5);
            System.out.println(consumerRecords);
            System.out.println(consumerRecords.count());
        }
    }

}