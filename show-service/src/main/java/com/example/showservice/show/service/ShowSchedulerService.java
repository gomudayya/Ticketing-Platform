package com.example.showservice.show.service;

import com.example.showservice.show.domain.Show;
import com.example.showservice.show.dto.show.ShowSimpleResponse;
import com.example.showservice.show.repository.ShowRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShowSchedulerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ShowRepository showRepository;
    public static final String UPCOMING_TICKET_OPEN_TOPIC = "show-service.show-simple.upcoming-ticket-open";
    public static final int TICKET_OPEN_LOOKAHEAD_HOURS = 24; // 24시간 이내에 티켓 오픈이 시작되는 공연을 조회한다.

    @Transactional(readOnly = true)
    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 캐싱을 위한 이벤트 발행
    public void cacheShowUpcomingTicketOpenTime() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime next24Hours = now.plusHours(24);

        List<Show> shows = showRepository.findShowByTicketOpenTimeBetween(now, next24Hours);

        for (Show show : shows) {
            ShowSimpleResponse showResponse = ShowSimpleResponse.from(show);
            kafkaTemplate.send(UPCOMING_TICKET_OPEN_TOPIC, showResponse);
            
            log.info("[카프카 메시지 전송 완료] {} {}", UPCOMING_TICKET_OPEN_TOPIC, showResponse);
        }
    }
}
