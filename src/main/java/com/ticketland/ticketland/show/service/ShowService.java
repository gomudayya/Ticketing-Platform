package com.ticketland.ticketland.performance.service;

import com.ticketland.ticketland.global.exception.NotFoundException;
import com.ticketland.ticketland.performance.domain.Show;
import com.ticketland.ticketland.performance.dto.ShowSingleResponse;
import com.ticketland.ticketland.performance.repository.ShowRepository;
import lombok.RequiredArgsConstructor;
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
}
