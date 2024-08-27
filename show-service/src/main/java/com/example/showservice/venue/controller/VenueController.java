package com.example.showservice.venue.controller;

import com.example.servicecommon.auth.AllowedAuthority;
import com.example.servicecommon.auth.UserRole;
import com.example.showservice.venue.dto.VenueCreateRequest;
import com.example.showservice.venue.dto.VenueDetailResponse;
import com.example.showservice.venue.dto.VenuePageResponse;
import com.example.showservice.venue.service.VenueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/venues")
@RequiredArgsConstructor
public class VenueController {
    private final VenueService venueService;

    @PostMapping
    @AllowedAuthority(UserRole.Authority.ADMIN)
    public ResponseEntity<VenueDetailResponse> createVenue(@RequestBody @Valid VenueCreateRequest venueCreateRequest) {
        return ResponseEntity.ok(venueService.createVenue(venueCreateRequest));
    }

    @GetMapping("/search")
    @AllowedAuthority(UserRole.Authority.ADMIN)
    public ResponseEntity<VenuePageResponse> findVenue(@RequestParam(defaultValue = "") String keyword, Pageable pageable) {
        return ResponseEntity.ok(venueService.findVenue(keyword, pageable));
    }

    @GetMapping("/{venueId}")
    @AllowedAuthority(UserRole.Authority.ADMIN)
    public ResponseEntity<VenueDetailResponse> findVenue(@PathVariable Long venueId) {
        return ResponseEntity.ok(venueService.findVenue(venueId));
    }
}
