package com.ticketland.ticketland.show.service;

import com.ticketland.ticketland.global.exception.NotFoundException;
import com.ticketland.ticketland.show.domain.Show;
import com.ticketland.ticketland.show.dto.ShowSliceResponse;
import com.ticketland.ticketland.show.dto.ShowSearchCondition;
import com.ticketland.ticketland.show.dto.ShowSingleResponse;
import com.ticketland.ticketland.show.repository.ShowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShowService {
    private final ShowRepository showRepository;

    public ShowSingleResponse findById(Long showId) {
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new NotFoundException("공연"));

        return ShowSingleResponse.from(show);
    }

    public ShowSliceResponse findBy(ShowSearchCondition showSearchCondition, Pageable pageable) {
        Slice<Show> shows = showRepository.searchPage(showSearchCondition, pageable);
        return ShowSliceResponse.from(shows);
    }
}
