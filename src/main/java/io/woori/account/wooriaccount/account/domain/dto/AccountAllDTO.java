package io.woori.account.wooriaccount.account.domain.dto;

import io.woori.account.wooriaccount.account.domain.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountAllDTO {

	private Long accountId;
	private String accountNumber;
	private String balance;

	public static AccountAllDTO fromEntity(Account account) {

		return new AccountAllDTO(account.getAccountId(), account.getAccountNumber(),
			String.valueOf(account.getAccountBalance()));
	}
}
