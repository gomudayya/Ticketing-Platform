package com.example.orderservice.order.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderPurchaseRequest {
    @NotNull
    private Long showId;

    @NotEmpty
    @Size(max = 10)
    private List<SeatDto> seats;
}
