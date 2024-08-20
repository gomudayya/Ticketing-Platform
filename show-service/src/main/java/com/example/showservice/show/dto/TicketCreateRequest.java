package com.example.showservice.show.dto;

import lombok.AllArgsConstructor;
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

    private List<SeatInfo> seatInfos;

    @Getter
    @Setter
    @ToString
    public static class SeatInfo {
        private String section;
        private Long count;
        private Integer price;
    }
}
