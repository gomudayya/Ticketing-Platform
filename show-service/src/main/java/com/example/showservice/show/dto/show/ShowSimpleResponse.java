package com.example.showservice.show.dto.show;

import com.example.showservice.show.constant.ShowStatus;
import com.example.showservice.show.domain.Show;
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
    public static ShowSimpleResponse from(Show show) {
        return ShowSimpleResponse.builder()
                .showId(show.getId())
                .genre(show.getGenreName())
                .performer(show.getPerformer())
                .title(show.getTitle())
                .titleImage(show.getTitleImage())
                .ticketingTime(show.getTicketOpenTime())
                .startDate(show.getStartTime())
                .duration(show.getDuration())
                .showStatus(show.getShowStatus())
                .build();
    }
}
