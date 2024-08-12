package com.ticketland.ticketland.show.controller;

import com.ticketland.ticketland.show.dto.venue.VenueCreateRequest;
import com.ticketland.ticketland.show.dto.venue.VenueDetailResponse;
import com.ticketland.ticketland.show.dto.venue.VenueResponse;
import com.ticketland.ticketland.show.service.VenueService;
import com.ticketland.ticketland.user.constant.UserRole;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/venues")
@RequiredArgsConstructor
public class VenueController {
    private final VenueService venueService;

    @PostMapping
    @Secured(UserRole.Authority.ADMIN)
    public ResponseEntity<VenueDetailResponse> createVenue(@RequestBody @Valid VenueCreateRequest venueCreateRequest) {
        VenueDetailResponse venueDetailResponse = venueService.createVenue(venueCreateRequest);

        return ResponseEntity.ok(venueDetailResponse);
    }
}
