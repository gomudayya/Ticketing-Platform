package com.ticketland.ticketland.performance.controller;

import com.ticketland.ticketland.performance.dto.ShowSingleResponse;
import com.ticketland.ticketland.performance.service.ShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shows")
@RequiredArgsConstructor
public class ShowController {

    private final ShowService showService;

    @GetMapping("/{showId}")
    public ResponseEntity<ShowSingleResponse> findShow(@PathVariable Long showId) {
        return ResponseEntity.ok(showService.findById(showId));
    }
}
