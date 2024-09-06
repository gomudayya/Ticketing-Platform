package com.example.showservice.genre.service;

import com.example.showservice.genre.dto.GenreCreateRequest;
import com.example.showservice.genre.dto.GenreCreateResponse;
import com.example.showservice.genre.repository.GenreRepository;
import com.example.showservice.testutil.IntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class GenreServiceTest extends IntegrationTest {

    @Autowired
    GenreService genreService;

    @Autowired
    GenreRepository genreRepository;
    @Test
    @DisplayName("장르 생성 메서드 테스트")
    void createGenre() {
        //given
        String genreName = "콘서트";
        GenreCreateRequest request = new GenreCreateRequest();
        request.setGenreName(genreName);

        //when
        GenreCreateResponse response = genreService.createGenre(request);
        Long genreId = response.getGenreId();

        //then
        assertThat(response.getGenreName()).isEqualTo(genreName);
        assertThat(genreId).isNotNull();
        assertThat(genreRepository.findById(genreId).get().getGenreName()).isEqualTo(genreName);
    }
}