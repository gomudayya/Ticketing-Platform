package com.example.showservice.genre.controller;

import com.example.servicecommon.auth.AllowedAuthority;
import com.example.servicecommon.auth.UserRole;
import com.example.showservice.genre.dto.GenreCreateRequest;
import com.example.showservice.genre.dto.GenreCreateResponse;
import com.example.showservice.genre.service.GenreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/genres")
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @AllowedAuthority(UserRole.Authority.ADMIN)
    @PostMapping
    public ResponseEntity<GenreCreateResponse> createGenre(@Valid @RequestBody GenreCreateRequest genreCreateRequest) {
        return ResponseEntity.ok(genreService.createGenre(genreCreateRequest));
    }
}
