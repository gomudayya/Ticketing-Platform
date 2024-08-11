package com.ticketland.ticketland.show.dto;

import com.ticketland.ticketland.show.domain.Show;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShowPageResponse {

    List<ShowSingleResponse> data;

    public static ShowPageResponse from(Page<Show> shows) {
        List<ShowSingleResponse> data = shows.getContent().stream()
                .map(ShowSingleResponse::from)
                .toList();

        return new ShowPageResponse(data);
    }
}
