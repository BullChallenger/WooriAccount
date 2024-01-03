package io.woori.account.wooriaccount.repository.jpa;

import io.woori.account.wooriaccount.domain.entity.Account;
import io.woori.account.wooriaccount.domain.entity.Customer;
import io.woori.account.wooriaccount.dto.user.SignUpRequestDTO;
import io.woori.account.wooriaccount.dummy.DummyCustomer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.EmptyResultDataAccessException;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AccountRepositoryTest {
    private Account account;
    private Customer customer;

    @Autowired
    private AccountRepository repository;

    @Autowired
    TestEntityManager testEntityManager;


    @BeforeEach
    void setUp() {
        SignUpRequestDTO dto = new SignUpRequestDTO("test name","01011111111" , "test@test.com", "test pw");
        customer= DummyCustomer.dummy(dto);

        testEntityManager.persist(customer);


        account = Account.builder()
                .accountNumber("99998888")
                .accountBalance(BigDecimal.valueOf(1000))
                .accountLimit(BigDecimal.valueOf(1000000))
                .customer(customer)
                .build();

        testEntityManager.persist(account);


    }

    @DisplayName("pk값으로 해당 계좌 찾아오는 테스트 - 성공")
    @Test
    public void testFindById(){
        Long id =1L;

        Optional<Account> op = repository.findById(id);

        assertThat(op.isPresent());

    }

    @DisplayName("pk값으로 해당 계좌 찾아오는 테스트 - 실패")
    @Test
    public void testFindById_fail(){
        Long id =5L;

        Optional<Account> op = repository.findById(id);

        assertThat(op.isPresent()).isFalse();

    }



    @DisplayName("계좌 삭제 테스트 - 성공")
    @Test
    public void testDeleteById(){
        Long id = 1L;

        repository.deleteById(id);

        Optional<Account> op = repository.findById(id);
        assertThat(op.isPresent()).isFalse();
    }

    @DisplayName("계좌 삭제 테스트 - 실패")
    @Test
    public void testDeleteById_fail(){
        Long id = 5L;

        assertThatThrownBy(()-> repository.deleteById(id)).isExactlyInstanceOf(EmptyResultDataAccessException.class);

    }

    @DisplayName("계좌 저장 테스트")
    @Test
    public void testSave(){

        Account save = repository.save(account);
        assertThat(save.getAccountId()).isEqualTo(customer.getCustomerId());
    }


    @DisplayName("계좌 번호로 해당 계좌 찾는 테스트 - 성공")
    @Test
    public void testFiindByAccountNumber(){
        String accountNumber = "99998888";

        Optional<Account> op = repository.findByAccountNumber(accountNumber);

        assertThat(op.isPresent());
    }
    @DisplayName("계좌 번호로 해당 계좌 찾는 테스트 - 실패")
    @Test
    public void testFiindByAccountNumber_fail(){
        String accountNumber = "4444444444";

        Optional<Account> op = repository.findByAccountNumber(accountNumber);

        assertThat(op.isPresent()).isFalse();
    }




}