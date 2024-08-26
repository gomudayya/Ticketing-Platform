package com.example.userservice.client.showservice.dto;

import com.example.userservice.client.showservice.constant.ShowStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShowSimpleResponse {
    private Long showId;
    private String genre;
    private String performer;
    private String title;
    private String titleImage;
    private LocalDateTime ticketingTime;
    private LocalDateTime startDate;
    private Integer duration; // 공연시간 (분)
    private ShowStatus showStatus;
}
