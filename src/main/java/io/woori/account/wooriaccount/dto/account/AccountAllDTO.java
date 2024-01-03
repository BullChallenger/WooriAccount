package io.woori.account.wooriaccount.dto.account;


import io.woori.account.wooriaccount.domain.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountAllDTO {

    private String accountNumber;
    private String balance;

    public static AccountAllDTO fromEntity(Account account){

        return new AccountAllDTO(account.getAccountNumber(), String.valueOf(account.getAccountBalance()));

    }

}
