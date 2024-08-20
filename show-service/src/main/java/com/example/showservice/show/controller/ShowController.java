package com.example.showservice.show.controller;

import com.example.servicecommon.auth.AllowedAuthority;
import com.example.servicecommon.auth.UserRole;
import com.example.showservice.show.dto.show.ShowCreateRequest;
import com.example.showservice.show.dto.show.ShowDetailResponse;
import com.example.showservice.show.dto.show.ShowSearchCondition;
import com.example.showservice.show.dto.show.ShowSliceResponse;
import com.example.showservice.show.service.ShowService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shows")
@RequiredArgsConstructor
public class ShowController {

    private final ShowService showService;

    @GetMapping("/{showId}")
    public ResponseEntity<ShowDetailResponse> findShow(@PathVariable Long showId) {
        return ResponseEntity.ok(showService.findDetailShow(showId));
    }
    @GetMapping("/search")
    public ResponseEntity<ShowSliceResponse> findShow(@ModelAttribute @Valid ShowSearchCondition showSearchCondition, Pageable pageable) {
        return ResponseEntity.ok(showService.findBy(showSearchCondition, pageable));
    }

    @PostMapping
    @AllowedAuthority(UserRole.Authority.ADMIN)
    public ResponseEntity<?> createShow(@RequestBody @Valid ShowCreateRequest showCreateRequest) {
        return ResponseEntity.ok(showService.createShow(showCreateRequest));
    }
}
