package com.ticketland.ticketland.show.controller;

import com.ticketland.ticketland.show.dto.venue.VenueCreateRequest;
import com.ticketland.ticketland.show.dto.venue.VenueDetailResponse;
import com.ticketland.ticketland.show.dto.venue.VenuePageResponse;
import com.ticketland.ticketland.show.service.VenueService;
import com.ticketland.ticketland.user.constant.UserRole;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
    @Secured(UserRole.Authority.ADMIN)
    public ResponseEntity<VenueDetailResponse> createVenue(@RequestBody @Valid VenueCreateRequest venueCreateRequest) {
        return ResponseEntity.ok(venueService.createVenue(venueCreateRequest));
    }

    @GetMapping("/search")
    @Secured(UserRole.Authority.ADMIN)
    public ResponseEntity<VenuePageResponse> findVenue(@RequestParam String keyword, Pageable pageable) {
        return ResponseEntity.ok(venueService.findVenue(keyword, pageable));
    }

    @GetMapping("/{venueId}")
    @Secured(UserRole.Authority.ADMIN)
    public ResponseEntity<VenueDetailResponse> findVenue(@PathVariable Long venueId) {
        return ResponseEntity.ok(venueService.findVenue(venueId));
    }
}
