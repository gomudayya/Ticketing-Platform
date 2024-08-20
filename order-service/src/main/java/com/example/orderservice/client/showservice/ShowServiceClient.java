package com.example.orderservice.client.showservice;

import com.example.orderservice.client.showservice.dto.ShowDetailResponse;
import com.example.orderservice.client.showservice.dto.ShowSimpleResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "show-service")
public interface ShowServiceClient {

//    @GetMapping("/api/shows/{showId}")
//    ShowDetailResponse getShow(@PathVariable Long showId);

    @GetMapping("/internal/shows/{showId}")
    ShowSimpleResponse getShow(@PathVariable Long showId);

    @GetMapping("/internal/shows/bulk")
    List<ShowSimpleResponse> getShows(@RequestParam List<Long> showIds);
}
