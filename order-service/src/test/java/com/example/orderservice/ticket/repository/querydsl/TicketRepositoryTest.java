package com.example.orderservice.ticket.repository.querydsl;

import com.example.orderservice.fixture.ticket.TicketFixture;
import com.example.orderservice.testutil.JpaTest;
import com.example.orderservice.ticket.domain.Ticket;
import com.example.orderservice.ticket.repository.TicketRepository;
import com.example.orderservice.ticket.repository.dto.TicketStatusDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TicketRepositoryTest extends JpaTest {

    @Autowired
    TicketRepository ticketRepository;
    @Test
    @DisplayName("공연id 정보를 이용해 티켓을 조회할 때 올바르게 반환하는지 테스트 한다.")
    void findTicketSelectionInfo() {
        //given
        List<Ticket> tickets = TicketFixture.createTicketsWithShowId(1L, 5);
        List<Ticket> tickets2 = TicketFixture.createTicketsWithShowId(2L, 3);

        ticketRepository.saveAll(tickets);
        ticketRepository.saveAll(tickets2);

        //when
        List<TicketStatusDto> findTickets = ticketRepository.findTicketStatuses(1L);

        //then
        String ticketCodePrefix = String.format("%d_", 1L);
        assertThat(findTickets.size()).isEqualTo(5);
        findTickets.forEach(ticket -> assertThat(ticket.getTicketCode()).startsWith(ticketCodePrefix));
    }
}