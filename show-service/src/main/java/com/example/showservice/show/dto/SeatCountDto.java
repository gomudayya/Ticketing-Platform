package com.example.showservice.show.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeatCountDto {
    @NotBlank(message = "섹션 이름은 비워둘 수 없습니다.")
    private String section;

    @NotNull(message = "좌석 수는 비워둘 수 없습니다.")
    private Long count;
}
