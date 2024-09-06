package com.example.showservice.generator;

import com.example.showservice.genre.domain.Genre;
import com.example.showservice.show.domain.Show;
import com.example.showservice.show.repository.ShowRepository;
import com.example.showservice.venue.domain.Venue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ShowGenerator {

    @Autowired
    private GenreGenerator genreGenerator;

    @Autowired
    private VenueGenerator venueGenerator;
    @Autowired
    private ShowRepository showRepository;

    public List<Show> saveShows(LocalDateTime ticketOpenTime, int count) {
        Genre genre = genreGenerator.saveDefaultGenre();
        Venue venue = venueGenerator.saveDefaultVenue();

        List<Show> result = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Show show = Show.builder()
                    .ticketOpenTime(ticketOpenTime)
                    .title("test" + i)
                    .genre(genre)
                    .venue(venue)
                    .titleImage("test" + i)
                    .build();

            result.add(show);
        }

        return showRepository.saveAll(result);
    }
}
