package com.ticketland.ticketland.show.dto;

import com.ticketland.ticketland.show.domain.Show;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowSliceResponse {
    private Integer presentPage;
    private Boolean hasNext;
    private List<ShowSingleResponse> content;

    public static ShowSliceResponse from(Slice<Show> shows) {
        List<ShowSingleResponse> content = shows.getContent().stream()
                .map(ShowSingleResponse::from)
                .toList();


        return ShowSliceResponse.builder()
                .presentPage(shows.getNumber())
                .hasNext(shows.hasNext())
                .content(content)
                .build();
    }
}
