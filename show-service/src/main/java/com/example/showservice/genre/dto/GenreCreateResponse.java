package com.example.showservice.genre.dto;

import com.example.showservice.genre.domain.Genre;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GenreCreateResponse {
    private Long genreId;
    private String genreName;

    public static GenreCreateResponse from(Genre genre) {
        return builder()
                .genreId(genre.getId())
                .genreName(genre.getGenreName())
                .build();
    }
}
