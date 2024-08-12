package com.ticketland.ticketland.show.dto;

import com.ticketland.ticketland.show.domain.Show;
import com.ticketland.ticketland.show.domain.TicketPrice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TicketPriceDto {
    private String seatSection;
    private Integer price;

    public TicketPrice toEntity(Show show) {
        return TicketPrice.builder()
                .show(show)
                .price(price)
                .seatSection(seatSection)
                .build();
    }

    public static TicketPriceDto from(TicketPrice ticketPrice) {
        return new TicketPriceDto(ticketPrice.getSeatSection(), ticketPrice.getPrice());
    }
}
