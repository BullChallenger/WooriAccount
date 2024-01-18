package io.woori.account.wooriaccount.common.repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class EmitterRepository {

	private final Map<String, SseEmitter> emitterMap = new ConcurrentHashMap<>();

	public SseEmitter save(final String sseKey, SseEmitter sseEmitter) {
		emitterMap.put(sseKey, sseEmitter);
		log.info("Set SseEmitter {}", sseKey);
		return sseEmitter;
	}

	public Optional<Map<String, SseEmitter>> findAllByCustomerId(Long id) {
		Map<String, SseEmitter> matchedSseEmitter = new ConcurrentHashMap<>();

		emitterMap.forEach((key, sseEmitter) -> {
			if (key.startsWith(String.valueOf(id))) {
				matchedSseEmitter.put(key, sseEmitter);
			}
		});

		return Optional.of(matchedSseEmitter);
	}

	public void deleteById(final String sseKey) {
		emitterMap.remove(String.valueOf(sseKey));

	}

}
