package com.example.orderservice.ticket.service;

import com.example.orderservice.ticket.constant.TicketStatus;
import com.example.orderservice.ticket.domain.Ticket;
import com.example.orderservice.ticket.repository.TicketRepository;
import com.example.orderservice.ticket.repository.TicketStatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TicketCacheService {
    private final TicketRepository ticketRepository;
    private final TicketStatusRepository ticketStatusRepository; // cache (redis) 저장소
    private static final String CHECK_TICKET_STATUS_AND_SELECT_SCRIPT =
            """
                    local showId = KEYS[1]
                    local ticketCodes = ARGV
                    
                    for i, code in ipairs(ticketCodes) do
                        local status = redis.call('HGET', showId, code)
                        
                        if not status then
                            error("Ticket " .. code .. " does not exist or status is nil.")
                        end
                        
                        if status ~= "AVAILABLE" then
                            return 0
                        end
                    end
                    
                    for i, code in ipairs(ticketCodes) do
                        redis.call('HSET', showId, code, "SELECTED")
                    end
                    
                    return 1
            """;




    // (티켓팅 시간이 임박한) 티켓들을 캐싱한다.
    public void cacheTickets(Long showId) {
        List<Ticket> tickets = ticketRepository.findTicketByShowId(showId);
        Map<String, String> ticketStatuses = tickets.stream()
                .collect(Collectors.toMap(Ticket::getCode, ticket -> ticket.getTicketStatus().name())); // <티켓코드, 티켓상태>

        ticketStatusRepository.saveAll(showId, ticketStatuses);
    }

    public Map<String, String> getTicketStatuses(Long showId) {
        return ticketStatusRepository.findAll(showId);
    }

    public boolean isTicketsAvailable(Long showId, List<String> ticketCodes) {
        RedisScript<Boolean> redisScript = RedisScript.of(CHECK_TICKET_STATUS_AND_SELECT_SCRIPT, Boolean.class);

        return ticketStatusRepository.evalScript(
                redisScript,
                new ArrayList<>(List.of(showId.toString())),
                ticketCodes.toArray()
        );
    }

    public void changeTicketStatus(List<Ticket> tickets, TicketStatus ticketStatus) {
        tickets.forEach(ticket -> ticketStatusRepository.changeTicketStatus(ticket.getShowId(), ticket.getCode(), ticketStatus));
    }
}
