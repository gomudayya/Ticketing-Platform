package com.example.showservice.genre.repository;

import com.example.showservice.genre.domain.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
