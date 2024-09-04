package com.example.showservice.generator;

import com.example.showservice.show.domain.Genre;
import com.example.showservice.venue.domain.Venue;
import com.example.showservice.venue.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VenueGenerator {

    @Autowired
    private VenueRepository venueRepository;
    private static final Venue DEFAULT_VENUE = Venue.builder()
            .venueName("name")
            .address("address")
            .seatLayoutData("<svg>...<svg>")
            .seatCount(100)
            .build();

    public Venue saveDefaultVenue() {
        return venueRepository.save(DEFAULT_VENUE);
    }

}
