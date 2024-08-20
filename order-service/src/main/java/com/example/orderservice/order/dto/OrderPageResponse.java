package com.example.orderservice.order.dto;

import com.example.orderservice.client.showservice.dto.ShowSimpleResponse;
import com.example.orderservice.global.dto.PageMetaDto;
import com.example.orderservice.order.domain.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderPageResponse {
    private PageMetaDto pageMeta;
    private List<OrderSimpleResponse> orders;
    public static OrderPageResponse from(Page<Order> orderPage, List<ShowSimpleResponse> shows) {
        List<OrderSimpleResponse> list = new ArrayList<>();
        Map<Long, ShowSimpleResponse> showMap = shows.stream()
                .collect(Collectors.toMap(ShowSimpleResponse::getShowId, Function.identity()));

        for (Order order : orderPage.getContent()) {
            ShowSimpleResponse show = showMap.get(order.getShowId());
            list.add(OrderSimpleResponse.from(order, show));
        }

        return builder()
                .pageMeta(PageMetaDto.from(orderPage))
                .orders(list)
                .build();
    }
}
