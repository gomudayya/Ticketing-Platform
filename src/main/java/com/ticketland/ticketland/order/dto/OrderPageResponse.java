package com.ticketland.ticketland.order.dto;

import com.ticketland.ticketland.global.dto.PageMetaDto;
import com.ticketland.ticketland.order.domain.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;


@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderPageResponse {
    private PageMetaDto pageMeta;
    private List<OrderSimpleResponse> orders;
    public static OrderPageResponse from(Page<Order> orderPage) {
        List<OrderSimpleResponse> orderSimpleResponses = orderPage.getContent().stream()
                .map(OrderSimpleResponse::from)
                .toList();

        return builder()
                .pageMeta(PageMetaDto.from(orderPage))
                .orders(orderSimpleResponses)
                .build();
    }
}
