package com.example.showservice.client.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TicketCreateRequest {
    private Long showId;

    private List<SeatInfoDto> seatInfo;

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @Builder
    public static class SeatInfoDto {
        private String section;
        private Long count;
        private Integer price;
    }
}
