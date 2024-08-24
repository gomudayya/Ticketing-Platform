package com.example.orderservice.fixture.extrnal;

import com.example.orderservice.client.showservice.dto.ShowSimpleResponse;
import com.example.orderservice.client.showservice.dto.ShowStatus;

import java.time.LocalDateTime;

public class ShowSimpleResponseFixture {
    public static ShowSimpleResponse createTicketSaleActiveShow() {
        return ShowSimpleResponse.builder()
                .showId(1L)
                .genre("Drama")
                .performer("John Doe")
                .title("A Great Show")
                .titleImage("http://example.com/image.jpg")
                .ticketingTime(LocalDateTime.now().minusDays(1))
                .startDate(LocalDateTime.now().plusDays(10))
                .duration(120)
                .showStatus(ShowStatus.TICKET_SALE_ACTIVE)
                .build();
    }

    public static ShowSimpleResponse createBeforeTicketOpenShow() {
        return ShowSimpleResponse.builder()
                .showId(1L)
                .genre("Drama")
                .performer("John Doe")
                .title("A Great Show")
                .titleImage("http://example.com/image.jpg")
                .ticketingTime(LocalDateTime.now().minusDays(1))
                .startDate(LocalDateTime.now().plusDays(10))
                .duration(120)
                .showStatus(ShowStatus.BEFORE_TICKET_OPEN)
                .build();
    }
}
