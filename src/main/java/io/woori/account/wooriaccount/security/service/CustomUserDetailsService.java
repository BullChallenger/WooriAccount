package io.woori.account.wooriaccount.security.service;

import io.woori.account.wooriaccount.domain.entity.Customer;
import io.woori.account.wooriaccount.repository.jpa.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("CustomUserDetailsService : loadUserByUsername");

        Optional<Customer> op = customerRepository.findByCustomerEmail(username);
        Customer customer;
        CustomUserDetails userDetails;
        if (op.isPresent()){
            customer= op.get();
            userDetails = new CustomUserDetails(customer);

            return userDetails;
        }
        throw new BadCredentialsException("해당 이메일을 가진 회원이 존재하지 않습니다.");
    }
}
