package com.ticketland.ticketland.show.dto.show;

import com.ticketland.ticketland.global.dto.SlicePageDto;
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
    private SlicePageDto slicePageDto;
    private List<ShowSingleResponse> content;

    public static ShowSliceResponse from(Slice<Show> shows) {
        List<ShowSingleResponse> content = shows.getContent().stream()
                .map(ShowSingleResponse::from)
                .toList();


        return ShowSliceResponse.builder()
                .slicePageDto(SlicePageDto.from(shows))
                .content(content)
                .build();
    }
}
