package com.example.showservice.show.dto.seat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SeatDto {
    private String section; //좌석 섹션
    private Integer number; //좌석 번호
}
