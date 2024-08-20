package com.example.showservice.global.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SlicePageDto {
    private Integer currentPage;
    private Boolean hasNext;

    public static SlicePageDto from(Slice<?> slice) {
        return builder()
                .currentPage(slice.getNumber())
                .hasNext(slice.hasNext())
                .build();
    }
}
