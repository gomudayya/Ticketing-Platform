package com.ticketland.ticketland.show.controller;

import com.ticketland.ticketland.show.dto.ShowSliceResponse;
import com.ticketland.ticketland.show.dto.ShowSearchCondition;
import com.ticketland.ticketland.show.dto.ShowSingleResponse;
import com.ticketland.ticketland.show.service.ShowService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    @GetMapping("/search")
    public ResponseEntity<ShowSliceResponse> findShow(@ModelAttribute @Valid ShowSearchCondition showSearchCondition, Pageable pageable) {
        return ResponseEntity.ok(showService.findBy(showSearchCondition, pageable));
    }
}
