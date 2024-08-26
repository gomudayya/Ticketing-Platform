package com.example.orderservice.ticket.repository.dto;

import com.example.orderservice.ticket.constant.TicketStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TicketStatusDto {
    private String ticketCode;
    private TicketStatus ticketStatus;
}
