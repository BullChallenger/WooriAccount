package io.woori.account.wooriaccount.repository.jpa;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Optional;

import io.woori.account.wooriaccount.configuration.SecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.woori.account.wooriaccount.account.domain.entity.Account;
import io.woori.account.wooriaccount.account.repository.jpa.AccountRepository;
import io.woori.account.wooriaccount.customer.domain.dto.SignUpRequestDTO;
import io.woori.account.wooriaccount.customer.domain.entity.Customer;
import io.woori.account.wooriaccount.dummy.DummyCustomer;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AccountRepositoryTest {
	private Account account;
	private Customer customer;

	@Autowired
	private AccountRepository repository;

	@Autowired
	TestEntityManager testEntityManager;

	private PasswordEncoder encoder;

	@BeforeEach
	void setUp() {
		encoder = new BCryptPasswordEncoder();

		SignUpRequestDTO dto = new SignUpRequestDTO("test name", "01011111111", "test@test.com", "test pw");
		customer = DummyCustomer.dummy(dto, encoder.encode(dto.getCustomerPwd()));

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
	public void testFindById() {
		Long id = 1L;

		Optional<Account> op = repository.findById(id);

		assertThat(op.isPresent());

	}

	@DisplayName("pk값으로 해당 계좌 찾아오는 테스트 - 실패")
	@Test
	public void testFindById_fail() {
		Long id = 5L;

		Optional<Account> op = repository.findById(id);

		assertThat(op.isPresent()).isFalse();

	}

	@DisplayName("계좌 삭제 테스트 - 성공")
	@Test
	public void testDeleteById() {
		Long id = 1L;

		repository.deleteById(id);

		Optional<Account> op = repository.findById(id);
		assertThat(op.isPresent()).isFalse();
	}

	@DisplayName("계좌 삭제 테스트 - 실패")
	@Test
	public void testDeleteById_fail() {
		Long id = 5L;

		assertThatThrownBy(() -> repository.deleteById(id))
				.isExactlyInstanceOf(EmptyResultDataAccessException.class);

	}

	@DisplayName("계좌 저장 테스트")
	@Test
	public void testSave() {

		Account save = repository.save(account);
		assertThat(save.getAccountId()).isEqualTo(customer.getCustomerId());
	}

	@DisplayName("계좌 번호로 해당 계좌 찾는 테스트 - 성공")
	@Test
	public void testFiindByAccountNumber() {
		String accountNumber = "99998888";

		Optional<Account> op = repository.findByAccountNumber(accountNumber);

		assertThat(op.isPresent());
	}

	@DisplayName("계좌 번호로 해당 계좌 찾는 테스트 - 실패")
	@Test
	public void testFiindByAccountNumber_fail() {
		String accountNumber = "4444444444";

		Optional<Account> op = repository.findByAccountNumber(accountNumber);

		assertThat(op.isPresent()).isFalse();
	}


}