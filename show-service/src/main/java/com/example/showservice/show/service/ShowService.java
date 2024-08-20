package com.example.showservice.show.service;

import com.example.servicecommon.exception.NotFoundException;
import com.example.showservice.show.client.TicketServiceClient;
import com.example.showservice.show.domain.Genre;
import com.example.showservice.show.domain.Show;
import com.example.showservice.show.domain.Venue;
import com.example.showservice.show.dto.TicketCreateRequest;
import com.example.showservice.show.dto.TicketPriceDto;
import com.example.showservice.show.dto.show.ShowCreateRequest;
import com.example.showservice.show.dto.show.ShowDetailResponse;
import com.example.showservice.show.dto.show.ShowSearchCondition;
import com.example.showservice.show.dto.show.ShowSimpleResponse;
import com.example.showservice.show.dto.show.ShowSliceResponse;
import com.example.showservice.show.repository.GenreRepository;
import com.example.showservice.show.repository.SeatRepository;
import com.example.showservice.show.repository.ShowRepository;
import com.example.showservice.show.repository.VenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ShowService {
    private final ShowRepository showRepository;
    private final GenreRepository genreRepository;
    private final VenueRepository venueRepository;
    private final TicketPriceService ticketPriceService;
    private final SeatRepository seatRepository;
    private final TicketServiceClient ticketServiceClient;
    @Transactional(readOnly = true)
    public ShowDetailResponse findDetailShow(Long showId) {
        Show show = findShow(showId);
        return ShowDetailResponse.from(show);
    }

    @Transactional(readOnly = true)
    public ShowSimpleResponse findSimpleShow(Long showId) {
        Show show = findShow(showId);
        return ShowSimpleResponse.from(show);
    }

    @Transactional(readOnly = true)
    public ShowSliceResponse findBy(ShowSearchCondition showSearchCondition, Pageable pageable) {
        Slice<Show> shows = showRepository.searchPage(showSearchCondition, pageable);
        return ShowSliceResponse.from(shows);
    }

    public ShowDetailResponse createShow(ShowCreateRequest request) {
        Genre genre = genreRepository.findById(request.getGenreId()).orElseThrow(() -> new NotFoundException("장르"));
        Venue venue = venueRepository.findById(request.getVenueId()).orElseThrow(() -> new NotFoundException("공연장소"));

        Show show = request.toEntity(genre, venue);
        showRepository.save(show);
        ticketPriceService.saveTicketPrices(show, request.getTicketPrices()); // 공연의 티켓별 가격표를 저장

        requestTicketGenerate(show.getId(), venue.getId(), request.getTicketPrices());
        return ShowDetailResponse.from(show);
    }

    private void requestTicketGenerate(Long showId, Long venueId, List<TicketPriceDto> ticketPrices) {
        List<TicketCreateRequest.SeatInfo> seatInfos = seatRepository.findSeatCounts(venueId);

        //좌석별 가격 설정
        for (var seatInfo : seatInfos) {
            for (var ticketPrice : ticketPrices) {
                if (seatInfo.getSection().equals(ticketPrice.getSeatSection())) {
                    seatInfo.setPrice(ticketPrice.getPrice());
                }
            }
        }

        TicketCreateRequest ticketCreateRequest = new TicketCreateRequest(showId, seatInfos);
        ticketServiceClient.createTicket(ticketCreateRequest);
    }

    private Show findShow(Long showId) {
        return showRepository.findById(showId)
                .orElseThrow(() -> new NotFoundException("공연"));
    }

    public List<ShowSimpleResponse> findBulkShow(List<Long> showIds) {
        return showRepository.findByIdIn(showIds).stream()
                .map(ShowSimpleResponse::from)
                .collect(Collectors.toList());
    }
}
