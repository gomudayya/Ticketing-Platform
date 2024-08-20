package com.example.showservice.show.dto.show;

import com.example.showservice.global.dto.SlicePageDto;
import com.example.showservice.show.domain.Show;
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
    private SlicePageDto pageMeta;
    private List<ShowSimpleResponse> content;

    public static ShowSliceResponse from(Slice<Show> shows) {
        List<ShowSimpleResponse> content = shows.getContent().stream()
                .map(ShowSimpleResponse::from)
                .toList();


        return ShowSliceResponse.builder()
                .pageMeta(SlicePageDto.from(shows))
                .content(content)
                .build();
    }
}
