package io.woori.account.wooriaccount.dto.account;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import io.woori.account.wooriaccount.domain.entity.AbstractTxHistory;
import io.woori.account.wooriaccount.domain.entity.Account;
import io.woori.account.wooriaccount.domain.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
	private Long accountId; // 계좌 id

    private String accountNumber; // 계좌 번호

    private BigDecimal accountBalance; // 계좌 잔고

    private BigDecimal accountLimit; // 계좌 한도

    private String customerName; // 계좌 주인
    
    
    /* Entity -> DTO로 변환할 때 사용하는 메서드입니다.
     * 해당 메서드는 계좌와 관련된 응답값을 내려줄 때 사용합니다.
     * 
     * @param account 레포지토리에서 받아온 계좌 정보입니다.
     * @return AccountDTO 객체입니다.
     * */
    public static AccountDTO fromEntity(Account account) {
    	
    	return AccountDTO.builder()
    			.accountId(account.getAccountId())
    			.accountNumber(account.getAccountNumber())
    			.accountLimit(account.getAccountLimit())
    			.customerName(account.getCustomer().getCustomerName())
    			.build();
    	
    }

}
