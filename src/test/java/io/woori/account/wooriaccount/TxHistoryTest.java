package io.woori.account.wooriaccount;

import io.woori.account.wooriaccount.domain.entity.AbstractTxHistory;
import io.woori.account.wooriaccount.domain.entity.Account;
import io.woori.account.wooriaccount.domain.entity.Customer;
import io.woori.account.wooriaccount.domain.entity.DepositTxHistory;
import io.woori.account.wooriaccount.repository.jpa.AccountRepository;
import io.woori.account.wooriaccount.repository.jpa.CustomerRepository;
import io.woori.account.wooriaccount.repository.jpa.TxHistoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import static org.junit.Assert.assertEquals;

@SpringBootTest
public class TxHistoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TxHistoryRepository depositTxHistoryRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @Transactional
    public void test() {
        Customer customer = new Customer("조승빈", "010-1111-1234", "test@test.com", "1234");
        customerRepository.save(customer);

        Account sender = new Account("11111-1111", BigDecimal.valueOf(1000L), BigDecimal.valueOf(100000L), customer);
        accountRepository.save(sender);

        Customer customer2 = new Customer("빈승조", "010-2222-1234", "test2@test.com", "1234");
        customerRepository.save(customer);

        Account receiver = new Account("22222-22222", BigDecimal.valueOf(1000L), BigDecimal.valueOf(100000L), customer);
        accountRepository.save(receiver);

        BigDecimal amount = BigDecimal.valueOf(500L);
        depositTxHistoryRepository.save(DepositTxHistory.of(sender, receiver, amount, receiver.getAccountBalance().add(amount), "메모"));

        entityManager.flush();

        AbstractTxHistory byId = depositTxHistoryRepository.findById(1L).orElseThrow();
        Assertions.assertEquals(byId.getClass(), DepositTxHistory.class);
    }

}
