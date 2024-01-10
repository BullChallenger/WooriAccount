package io.woori.account.wooriaccount.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Repository
public class EmitterRepository {

    private final Map<Long, SseEmitter> emitterMap = new ConcurrentHashMap<>();

    public SseEmitter save(final Long id, SseEmitter sseEmitter) {
        emitterMap.put(id, sseEmitter);
        log.info("Set SseEmitter {}", id);
        return sseEmitter;
    }

    public void delete(final Long id) {
        emitterMap.remove(id);
    }

}
