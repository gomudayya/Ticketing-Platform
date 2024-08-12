package com.ticketland.ticketland.show.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TicketPriceDto {
    private String seatSection;
    private Integer price;
}
