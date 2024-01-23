package io.woori.account.wooriaccount.customer.repository;

import java.util.Optional;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.woori.account.wooriaccount.customer.domain.dto.CacheCustomerDTO;
import io.woori.account.wooriaccount.customer.domain.entity.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CustomerCacheRepository {

	private final ObjectMapper objectMapper;
	private final RedisTemplate<String, Object> redisTemplate;

	public void saveCustomerInCache(final Customer customer) throws JsonProcessingException {
		CacheCustomerDTO dto = CacheCustomerDTO.from(customer);

		String key = generateCacheKey(dto.getCustomerEmail());
		if (findCustomerByKey(key).isPresent()) {
			redisTemplate.delete(key);
		}

		log.info("Save Customer in Redis {} : {}", key, dto);
		redisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(dto));
	}

	public Optional<CacheCustomerDTO> findCustomerByKey(String key) {
		return Optional.ofNullable((CacheCustomerDTO)redisTemplate.opsForValue().get(key));
	}

	private String generateCacheKey(String email) {
		return "CUSTOMER: " + email;
	}

}
