package com.ticketland.ticketland.show.dto.show;

import com.ticketland.ticketland.show.domain.Show;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShowSingleResponse {
    private Long showId;
    private String genre;
    private String performer;
    private String title;
    private String descriptionImage;
    private LocalDateTime ticketingTime;
    private LocalDateTime startDate;
    private Integer duration; // 공연시간 (분)

    public static ShowSingleResponse from(Show show) {
        return ShowSingleResponse.builder()
                .showId(show.getId())
                .genre(show.getGenreName())
                .performer(show.getPerformer())
                .title(show.getTitle())
                .descriptionImage(show.getDescriptionImage())
                .ticketingTime(show.getTicketingTime())
                .startDate(show.getStartTime())
                .duration(show.getDuration())
                .build();
    }
}
