package io.woori.account.wooriaccount.repository.jpa;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.woori.account.wooriaccount.account.domain.entity.Account;
import io.woori.account.wooriaccount.customer.domain.dto.SignUpRequestDTO;
import io.woori.account.wooriaccount.customer.domain.entity.Customer;
import io.woori.account.wooriaccount.dummy.DummyCustomer;
import io.woori.account.wooriaccount.txhistory.repository.jpa.DepositTxHistoryRepository;
import io.woori.account.wooriaccount.txhistory.repository.jpa.WithdrawTxHistoryRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TxHistoryRepositoryTest {

	private Account sender;
	private Account receiver;
	private Customer senderCustomer;
	private Customer receiverCustomer;

	@Autowired
	private DepositTxHistoryRepository depositTxHistoryTxHistoryRepository;

	@Autowired
	private WithdrawTxHistoryRepository withdrawTxHistoryTxHistoryRepository;

	@Autowired
	TestEntityManager testEntityManager;

	private PasswordEncoder encoder;

	@BeforeEach
	void setUp() {

		SignUpRequestDTO dto = new SignUpRequestDTO("test name", "01011111111", "test@test.com", "test pw");
		SignUpRequestDTO dto2 = new SignUpRequestDTO("test2", "01022222222", "test2@test.com", "test2");

		senderCustomer = DummyCustomer.dummy(dto, encoder.encode(dto.getCustomerPhone()));
		receiverCustomer = DummyCustomer.dummy(dto2, encoder.encode(dto.getCustomerPhone()));

		testEntityManager.persist(senderCustomer);
		testEntityManager.persist(receiverCustomer);

		sender = Account.builder()
			.accountNumber("99998888")
			.accountBalance(BigDecimal.valueOf(1000))
			.accountLimit(BigDecimal.valueOf(1000000))
			.customer(senderCustomer)
			.build();

		receiver = Account.builder()
			.accountNumber("11112222")
			.accountBalance(BigDecimal.valueOf(1000))
			.accountLimit(BigDecimal.valueOf(1000000))
			.customer(receiverCustomer)
			.build();

		testEntityManager.persist(sender);
		testEntityManager.persist(receiver);

	}

	@DisplayName("")
	@Test
	void testFindById() {

	}

	@DisplayName("")
	@Test
	void deleteById() {

	}

	@DisplayName("")
	@Test
	void testSave() {

	}

	@DisplayName("")
	@Test
	void testFindBySenderAccountIdOrReceiverAccountIdOrderByCreatedTimeDesc() {

	}

}