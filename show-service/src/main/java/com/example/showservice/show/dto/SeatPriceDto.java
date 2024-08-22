package com.example.showservice.show.dto;

import com.example.showservice.show.domain.ShowSeat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SeatPriceDto {
    private String seatSection;
    private Integer price;

    public static SeatPriceDto from(ShowSeat showSeat) {
        return new SeatPriceDto(showSeat.getSeatSection(), showSeat.getPrice());
    }
}
