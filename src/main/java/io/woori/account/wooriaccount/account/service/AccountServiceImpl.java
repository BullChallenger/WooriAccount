package io.woori.account.wooriaccount.account.service;

import static io.woori.account.wooriaccount.common.constant.NotificationType.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.woori.account.wooriaccount.account.domain.dto.AccountAllDTO;
import io.woori.account.wooriaccount.account.domain.dto.AccountDTO;
import io.woori.account.wooriaccount.account.domain.dto.AccountRemittanceDTO;
import io.woori.account.wooriaccount.account.domain.entity.Account;
import io.woori.account.wooriaccount.account.repository.jpa.AccountRepository;
import io.woori.account.wooriaccount.account.repository.querydsl.QueryAccountRepository;
import io.woori.account.wooriaccount.account.service.inter.AccountService;
import io.woori.account.wooriaccount.common.constant.NotificationType;
import io.woori.account.wooriaccount.common.domain.NotificationArgs;
import io.woori.account.wooriaccount.common.domain.entity.Notification;
import io.woori.account.wooriaccount.common.exception.CustomException;
import io.woori.account.wooriaccount.common.exception.ErrorCode;
import io.woori.account.wooriaccount.common.repository.jpa.NotificationRepository;
import io.woori.account.wooriaccount.common.service.inter.NotificationService;
import io.woori.account.wooriaccount.customer.domain.entity.Customer;
import io.woori.account.wooriaccount.customer.repository.jpa.CustomerRepository;
import io.woori.account.wooriaccount.txhistory.domain.DepositTxHistory;
import io.woori.account.wooriaccount.txhistory.domain.WithdrawTxHistory;
import io.woori.account.wooriaccount.txhistory.repository.jpa.DepositTxHistoryRepository;
import io.woori.account.wooriaccount.txhistory.repository.jpa.WithdrawTxHistoryRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {

	private final AccountRepository accountRepository;
	private final QueryAccountRepository queryAccountRepository;
	private final CustomerRepository customerRepository;
	private final NotificationRepository notificationRepository;
	private final DepositTxHistoryRepository depositTxHistoryRepository;
	private final WithdrawTxHistoryRepository withdrawTxHistoryRepository;

	private final NotificationService notificationService;

	@Override
	public AccountDTO accountInquiry(String accountNumber) {
		Optional<Account> optionalAccount = accountRepository.findByAccountNumber(accountNumber);
		Account account = optionalAccount.orElseThrow(() ->
			new CustomException(ErrorCode.ACCOUNT_NOT_FOUND)
		);

		return AccountDTO.fromEntity(account);
	}

	@Override
	@Transactional
	public AccountDTO accountRemittance(AccountRemittanceDTO dto) {

		//출금 계좌 조회
		final Account sourceAccount = accountRepository.findByAccountNumber(dto.getAccountNumber())
			.orElseThrow(() -> new CustomException(ErrorCode.ACCOUNT_NOT_FOUND));

		// 입금 계좌 조회
		final Account targetAccount = accountRepository.findByAccountNumber(dto.getTargetAccountNumber())
			.orElseThrow(() -> new CustomException(ErrorCode.ACCOUNT_NOT_FOUND));

		// 잔액 확인
		if (sourceAccount.getAccountBalance().compareTo(BigDecimal.valueOf(Long.parseLong(dto.getAmount()))) < 0) {
			throw new CustomException(ErrorCode.INSUFFICIENT_FUNDS);
		}

		// 출금 계좌에서 금액 차감
		sourceAccount.setAccountBalance(
			sourceAccount.getAccountBalance().subtract(BigDecimal.valueOf(Long.parseLong(dto.getAmount()))));

		// 입금 계좌에 금액 추가
		targetAccount.setAccountBalance(
			targetAccount.getAccountBalance().add(BigDecimal.valueOf(Long.parseLong(dto.getAmount()))));

		// 거래 내역 기록
		WithdrawTxHistory withedDrawCreateTransactionHistory = withDrawCreateTransactionHistory(sourceAccount,
			targetAccount, BigDecimal.valueOf(Long.parseLong(dto.getAmount())), dto.getDescription());
		DepositTxHistory depositCreateTransactionHistory = depositCreateTransactionHistory(sourceAccount, targetAccount,
			BigDecimal.valueOf(Long.parseLong(dto.getAmount())), dto.getDescription());

		withdrawTxHistoryRepository.save(withedDrawCreateTransactionHistory);
		depositTxHistoryRepository.save(depositCreateTransactionHistory);

		notifyTx(dto, targetAccount, sourceAccount);

		return AccountDTO.fromEntity(sourceAccount);

	}

	@Transactional
	@Override
	public AccountDTO accountCreate(Long customerId) {
		customerRepository.findById(customerId).orElseThrow();

		Customer customer = customerRepository.findById(customerId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CUSTOMER));

		//8자리 난수 생성
		int randomNumber = (int)(Math.random() * 89999999) + 10000000;

		//생성한 난수 번호가 이미 존재한다면 사용하지 못하게 한다.
		Optional<Account> op = accountRepository.findByAccountNumber(String.valueOf(randomNumber));

		if (op.isPresent()) {
			throw new CustomException(ErrorCode.NOT_FOUND_ACCOUNT);
		}

		// 기본값으로 fix
		Account account = Account.createAccount(String.valueOf(randomNumber), BigDecimal.valueOf(1000),
			BigDecimal.valueOf(1000000), customer);
		accountRepository.save(account);

		return AccountDTO.builder()
			.accountId(account.getAccountId())
			.accountNumber(account.getAccountNumber())
			.accountBalance(account.getAccountBalance())
			.accountLimit(account.getAccountLimit())
			.customerName(customer.getCustomerName())
			.build();

	}

	@Override
	public AccountDTO accountDelete(String accountNumber) {

		Account account = accountRepository.findByAccountNumber(accountNumber)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ACCOUNT));

		Account deletedAccount = account.deletedAccount(account.getAccountNumber());

		return AccountDTO.fromEntity(deletedAccount);
	}

	@Override
	public List<AccountAllDTO> findAllByCustomerId(Long id) {

		List<Account> accounts = queryAccountRepository.queryFindAllByCustomerId(id);
		if (Objects.isNull(accounts)) {

			throw new CustomException(ErrorCode.NOT_FOUND_ACCOUNT_LIST);

		}

		List<AccountAllDTO> list = extractedDTO(accounts);

		return list;
	}

	private static List<AccountAllDTO> extractedDTO(List<Account> accounts) {
		List<AccountAllDTO> list = new ArrayList<>();
		for (Account account : accounts) {

			list.add(AccountAllDTO.fromEntity(account));
		}

		return list;
	}

	private WithdrawTxHistory withDrawCreateTransactionHistory(Account sourceAccount,
		Account targetAccount,
		BigDecimal amount, String description) {

		return WithdrawTxHistory.of(sourceAccount, targetAccount, amount, sourceAccount.getAccountBalance(),
			description);

	}

	private DepositTxHistory depositCreateTransactionHistory(Account sourceAccount, Account targetAccount,
		BigDecimal amount,
		String description) {

		return DepositTxHistory.of(sourceAccount, targetAccount, amount, targetAccount.getAccountBalance(),
			description);

	}

	private void notifyTx(AccountRemittanceDTO dto, Account to, Account from) {
		// List 사용 이유 : 추후 N 명에게 동시에 출금할 수 있도록 고도화 예정이기 때문에 돈을 입금 받는 계좌의 id 값을
		// List 로 받아서 알람을 전달
		List<Long> targetAccountIds = new ArrayList<>();
		targetAccountIds.add(to.getAccountId());
		List<Long> sourceAccountIds = new ArrayList<>();
		sourceAccountIds.add(from.getAccountId());

		// 입금 대상에게 알람 전달
		generateNotification(dto, to, from, DEPOSIT_TX_OCCUR, targetAccountIds);
		// 출금한 대상에게 알람 전달
		generateNotification(dto, from, to, WITHDRAW_TX_OCCUR, sourceAccountIds);
	}

	private void generateNotification(AccountRemittanceDTO dto, Account to, Account from, NotificationType type,
		List<Long> targetAccountIds) {
		targetAccountIds.forEach(id -> {
			Customer fromCustomer = from.getCustomer();
			notificationService.notify(id,
				notificationRepository.save(
					Notification.of(to.getCustomer(),
						writeNotificationContent(fromCustomer, dto, type),
						type,
						NotificationArgs.of(from.getAccountId(), targetAccountIds)
					)
				)
			);
		});
	}

	private static String writeNotificationContent(Customer from, AccountRemittanceDTO dto, NotificationType type) {
		return from.getCustomerName() + "으로부터" + dto.getAmount() + type.getNotificationContent();
	}

}
