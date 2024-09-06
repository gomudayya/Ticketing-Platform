package com.example.showservice.genre.service;

import com.example.showservice.genre.domain.Genre;
import com.example.showservice.genre.dto.GenreCreateRequest;
import com.example.showservice.genre.dto.GenreCreateResponse;
import com.example.showservice.genre.repository.GenreRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;

    public GenreCreateResponse createGenre(@Valid GenreCreateRequest genreCreateRequest) {
        Genre genre = new Genre(genreCreateRequest.getGenreName());

        genreRepository.save(genre);
        return GenreCreateResponse.from(genre);
    }
}
