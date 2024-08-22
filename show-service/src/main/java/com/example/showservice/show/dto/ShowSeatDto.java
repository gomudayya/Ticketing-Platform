package com.example.showservice.show.dto;

import com.example.showservice.show.domain.ShowSeat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShowSeatDto {
    private String seatSection;
    private Integer price;
    private Integer stock;

    public static ShowSeatDto from(ShowSeat showSeat) {
        return builder()
                .seatSection(showSeat.getSeatSection())
                .stock(showSeat.getStock())
                .price(showSeat.getPrice())
                .build();
    }
}
