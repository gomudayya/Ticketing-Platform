package com.example.showservice.show.service;

import com.example.servicecommon.exception.NotFoundException;
import com.example.showservice.client.orderservice.OrderServiceClient;
import com.example.showservice.client.orderservice.dto.TicketCreateRequest;
import com.example.showservice.genre.domain.Genre;
import com.example.showservice.show.domain.Show;
import com.example.showservice.show.dto.SeatCountDto;
import com.example.showservice.show.dto.SeatPriceDto;
import com.example.showservice.show.dto.show.ShowCreateRequest;
import com.example.showservice.show.dto.show.ShowDetailResponse;
import com.example.showservice.show.dto.show.ShowSearchCondition;
import com.example.showservice.show.dto.show.ShowSimpleResponse;
import com.example.showservice.show.dto.show.ShowSliceResponse;
import com.example.showservice.genre.repository.GenreRepository;
import com.example.showservice.show.repository.ShowRepository;
import com.example.showservice.venue.domain.Venue;
import com.example.showservice.venue.exception.SeatSectionUnmatchedException;
import com.example.showservice.venue.repository.SeatRepository;
import com.example.showservice.venue.repository.VenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ShowService {
    private final ShowRepository showRepository;
    private final GenreRepository genreRepository;
    private final VenueRepository venueRepository;
    private final ShowSeatService showSeatService;
    private final SeatRepository seatRepository;
    private final OrderServiceClient orderServiceClient;
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

        List<SeatCountDto> seatCounts = seatRepository.findSeatCounts(venue.getId());
        checkSeatSectionEqual(seatCounts, request.getSeatPrice());

        showSeatService.saveShowSeatInfo(show, request.getSeatPrice(), seatCounts); // 공연의 좌석 정보를 저장. (좌석섹션별 좌석가격 및 좌석재고)

        requestTicketGenerate(show.getId(), request.getSeatPrice(), seatCounts);
        return ShowDetailResponse.from(show);
    }

    private void checkSeatSectionEqual(List<SeatCountDto> seatCounts, List<SeatPriceDto> seatPrice) {
        Set<String> sectionSet = seatCounts.stream().map(SeatCountDto::getSection).collect(Collectors.toSet());
        Set<String> requestSections = seatPrice.stream().map(SeatPriceDto::getSeatSection).collect(Collectors.toSet());

        if (!sectionSet.equals(requestSections)) {
            throw new SeatSectionUnmatchedException();
        }
    }

    private void requestTicketGenerate(Long showId,
                                       List<SeatPriceDto> seatPrices, List<SeatCountDto> seatCounts) {

        Map<String, Long> seatCountMap = new HashMap<>();
        seatCounts.forEach(seatCount -> seatCountMap.put(seatCount.getSection(), seatCount.getCount()));

        List<TicketCreateRequest.SeatInfoDto> seatInfos = new ArrayList<>();
        for (SeatPriceDto showPrice : seatPrices) {
            TicketCreateRequest.SeatInfoDto seatInfo = TicketCreateRequest.SeatInfoDto.builder()
                    .section(showPrice.getSeatSection())
                    .price(showPrice.getPrice())
                    .count((seatCountMap.get(showPrice.getSeatSection())))
                    .build();

            seatInfos.add(seatInfo);
        }

        TicketCreateRequest ticketCreateRequest = new TicketCreateRequest(showId, seatInfos);
        orderServiceClient.createTicket(ticketCreateRequest);
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
