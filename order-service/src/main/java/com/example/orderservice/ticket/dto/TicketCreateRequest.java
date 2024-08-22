package com.example.orderservice.ticket.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TicketCreateRequest {
    @NotNull(message = "공연 Id는 필수 입력 항목입니다.")
    private Long showId;

    @NotEmpty(message = "좌석 정보는 비어있을 수 없습니다.")
    private List<SeatInfoDto> seatInfo;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SeatInfoDto {
        private String section;
        private Integer count;
        private Integer price;
    }
}
