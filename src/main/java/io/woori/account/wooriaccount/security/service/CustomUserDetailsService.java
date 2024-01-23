package io.woori.account.wooriaccount.security.service;

import java.util.Optional;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.woori.account.wooriaccount.customer.domain.dto.CacheCustomerDTO;
import io.woori.account.wooriaccount.customer.domain.entity.Customer;
import io.woori.account.wooriaccount.customer.repository.CustomerCacheRepository;
import io.woori.account.wooriaccount.customer.repository.jpa.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final CustomerRepository customerRepository;
	private final CustomerCacheRepository customerCacheRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		log.info("CustomUserDetailsService : loadUserByUsername");

		Optional<CacheCustomerDTO> cacheCustomer = customerCacheRepository.findCustomerByKey("CUSTOMER: " + username);
		if (cacheCustomer.isPresent()) {
			log.info("Redis 에서 찾은 Customer");
			return new CustomUserDetails(cacheCustomer.get());
		}

		Optional<Customer> op = customerRepository.findByCustomerEmail(username);
		Customer customer;
		CustomUserDetails userDetails;
		if (op.isPresent()) {

			log.info("CustomUserDetailsService customer 찾았다!");

			customer = op.get();

			try {
				customerCacheRepository.saveCustomerInCache(customer);
			} catch (JsonProcessingException e) {
				log.info("Cache Repository 쪽 에러임");
				throw new RuntimeException(e.getMessage());
			}

			userDetails = new CustomUserDetails(customer);
			return userDetails;
		}

		throw new BadCredentialsException("해당 이메일을 가진 회원이 존재하지 않습니다.");
	}
}
