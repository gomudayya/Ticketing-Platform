package com.example.showservice.genre.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GenreCreateRequest {

    @NotBlank(message = "장르 이름은 비어있을 수 없습니다.")
    private String genreName;
}
