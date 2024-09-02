package com.example.orderservice.ticket.repository;

import com.example.orderservice.ticket.constant.TicketStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
public class TicketStatusRepository {

    private final RedisTemplate<String, Object> template;
    private final String KEY_PREFIX = "TICKET_STATUS:";

    public void saveAll(Long showId, Map<String, String> ticketStatuses) {
        template.opsForHash()
                .putAll(addPrefix(showId), ticketStatuses);
    }

    public Map<String, String> findAll(Long showId) {
        return template.<String, String>opsForHash()
                .entries(addPrefix(showId));
    }

    public void changeTicketStatus(Long showId, String ticketCode, TicketStatus ticketStatus) {
        template.opsForHash()
                .put(addPrefix(showId), ticketCode, ticketStatus.name());
    }

    public <T> T evalScript(RedisScript<T> redisScript, List<String> keys, Object[] args) {
        return template.execute(redisScript, addPrefix(keys), args);
    }

    private String addPrefix(Object key) {
        return KEY_PREFIX + key;
    }

    private List<String> addPrefix(List<String> keys) {
        return keys.stream()
                .map(this::addPrefix)
                .collect(Collectors.toList());
    }
}
