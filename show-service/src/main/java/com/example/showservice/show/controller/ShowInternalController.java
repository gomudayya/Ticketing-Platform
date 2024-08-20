package com.example.showservice.show.controller;

import com.example.showservice.show.dto.show.ShowSimpleResponse;
import com.example.showservice.show.service.ShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/internal/shows")
@RequiredArgsConstructor
public class ShowInternalController {

    private final ShowService showService;
    @GetMapping("/{showId}")
    public ResponseEntity<ShowSimpleResponse> findShow(@PathVariable Long showId) {
        return ResponseEntity.ok(showService.findSimpleShow(showId));
    }

    @GetMapping("/bulk")
    public ResponseEntity<List<ShowSimpleResponse>> findShows (@RequestParam List<Long> showIds) {
        return ResponseEntity.ok(showService.findBulkShow(showIds));
    }
}
