package com.example.orderservice.ticket.repository.querydsl;

import com.example.orderservice.global.config.QuerydslConfig;
import com.example.orderservice.ticket.repository.TicketRepository;
import com.example.orderservice.ticket.repository.dto.TicketStatusDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(QuerydslConfig.class)
class TicketRepositoryTest {

    @Autowired
    TicketRepository ticketRepository;


    @Test
    @DisplayName("공연id와 좌석 section 정보를 이용해 티켓을 조회할 때 올바르게 반환하는지 테스트 한다.")
    void findTicketSelectionInfo() {
        //given
        Long showId = 7L;
        String seatSection = "A";

        //when
        List<TicketStatusDto> findTickets = ticketRepository.findTicketStatus(showId, seatSection);

        //then
        String ticketCodePrefix = String.format("%d_%s_", showId, seatSection);
        findTickets.forEach(ticket -> assertThat(ticket.getTicketCode()).startsWith(ticketCodePrefix));
    }
}