package com.example.showservice.generator;

import com.example.showservice.genre.domain.Genre;
import com.example.showservice.genre.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GenreGenerator {

    @Autowired
    private GenreRepository genreRepository;
    private static final Genre DEFAULT_GENRE = new Genre("genre");

    public Genre saveDefaultGenre() {
        return genreRepository.save(DEFAULT_GENRE);
    }
}
