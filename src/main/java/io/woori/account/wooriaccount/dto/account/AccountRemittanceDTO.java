package io.woori.account.wooriaccount.dto.account;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountRemittanceDTO {


    private String accountNumber;

    private String targetAccountNumber;

    private String description;

    private String amount;

}
