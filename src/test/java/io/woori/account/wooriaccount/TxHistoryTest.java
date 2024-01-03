package io.woori.account.wooriaccount;

import io.woori.account.wooriaccount.domain.entity.AbstractTxHistory;
import io.woori.account.wooriaccount.domain.entity.Account;
import io.woori.account.wooriaccount.domain.entity.Customer;
import io.woori.account.wooriaccount.domain.entity.DepositTxHistory;
import io.woori.account.wooriaccount.dto.account.AccountRemittanceDTO;
import io.woori.account.wooriaccount.repository.jpa.AccountRepository;
import io.woori.account.wooriaccount.repository.jpa.CustomerRepository;
import io.woori.account.wooriaccount.repository.jpa.TxHistoryRepository;
import io.woori.account.wooriaccount.repository.querydsl.QueryTransactionHistoryRepositoryImpl;
import io.woori.account.wooriaccount.service.AccountServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
public class TxHistoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TxHistoryRepository<DepositTxHistory> depositTxHistoryRepository;

    @Autowired
    QueryTransactionHistoryRepositoryImpl queryTransactionHistoryRepository;

    @Autowired
    AccountServiceImpl accountService;

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

//        AbstractTxHistory byId = depositTxHistoryRepository.findById(1L).orElseThrow();
//        Assertions.assertEquals(byId.getClass(), DepositTxHistory.class);
    }

    @Test
    @Transactional
    @DisplayName(value = "비관적 락을 걸어 동시성 문제 해소")
    void txLockTest() throws InterruptedException {
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);
        AccountRemittanceDTO dto = new AccountRemittanceDTO("ACC123456",
                "52862517", "송금송금", "5");

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    accountService.accountRemittance(dto);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        Account acc123456 = accountRepository.findByAccountNumber("ACC123456").orElseThrow();
        System.out.println(acc123456.getAccountBalance());
        Assertions.assertEquals(0, BigDecimal.valueOf(4500).compareTo(acc123456.getAccountBalance()));
    }

}
