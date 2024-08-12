package com.ticketland.ticketland.show.service;

import com.ticketland.ticketland.global.exception.NotFoundException;
import com.ticketland.ticketland.show.domain.Genre;
import com.ticketland.ticketland.show.domain.Show;
import com.ticketland.ticketland.show.domain.Venue;
import com.ticketland.ticketland.show.dto.show.ShowCreateRequest;
import com.ticketland.ticketland.show.dto.show.ShowDetailResponse;
import com.ticketland.ticketland.show.dto.show.ShowSearchCondition;
import com.ticketland.ticketland.show.dto.show.ShowSliceResponse;
import com.ticketland.ticketland.show.repository.GenreRepository;
import com.ticketland.ticketland.show.repository.ShowRepository;
import com.ticketland.ticketland.show.repository.VenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ShowService {
    private final ShowRepository showRepository;
    private final GenreRepository genreRepository;
    private final VenueRepository venueRepository;
    private final TicketService ticketService;
    @Transactional(readOnly = true)
    public ShowDetailResponse findById(Long showId) {
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new NotFoundException("공연"));

        return ShowDetailResponse.from(show);
    }

    @Transactional(readOnly = true)
    public ShowSliceResponse findBy(ShowSearchCondition showSearchCondition, Pageable pageable) {
        Slice<Show> shows = showRepository.searchPage(showSearchCondition, pageable);
        return ShowSliceResponse.from(shows);
    }

    public ShowDetailResponse createShow(ShowCreateRequest showCreateRequest) {
        Long venueId = showCreateRequest.getVenueId();
        Long genreId = showCreateRequest.getGenreId();

        Genre genre = genreRepository.findById(genreId).orElseThrow(() -> new NotFoundException("장르"));
        Venue venue = venueRepository.findById(venueId).orElseThrow(() -> new NotFoundException("공연장소"));

        Show show = Show.builder()
                .genre(genre)
                .venue(venue)
                .title(showCreateRequest.getTitle())
                .performer(showCreateRequest.getPerformer())
                .descriptionImage(showCreateRequest.getDescriptionImage())
                .ticketingTime(showCreateRequest.getTicketingTime())
                .startTime(showCreateRequest.getStartDate())
                .duration(showCreateRequest.getDuration())
                .build();

        showRepository.save(show);
        ticketService.generateTickets(show, venue, showCreateRequest.getTicketPrices()); // 나중에 비동기 처리 해줘야함.
        return ShowDetailResponse.from(show);
    }
}
