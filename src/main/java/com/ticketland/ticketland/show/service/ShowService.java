package com.ticketland.ticketland.show.service;

import com.ticketland.ticketland.global.exception.NotFoundException;
import com.ticketland.ticketland.show.domain.Show;
import com.ticketland.ticketland.show.dto.ShowPageResponse;
import com.ticketland.ticketland.show.dto.ShowSearchCondition;
import com.ticketland.ticketland.show.dto.ShowSingleResponse;
import com.ticketland.ticketland.show.repository.ShowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShowService {
    private final ShowRepository showRepository;

    public ShowSingleResponse findById(Long showId) {
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new NotFoundException("해당 공연을 찾을 수 없습니다."));

        return ShowSingleResponse.from(show);
    }

    public ShowPageResponse findBy(ShowSearchCondition showSearchCondition, Pageable pageable) {
        Page<Show> shows = showRepository.searchPage(showSearchCondition, pageable);
        return ShowPageResponse.from(shows);
    }
}
