package io.woori.account.wooriaccount.service.inter;

import io.woori.account.wooriaccount.domain.entity.Account;
import io.woori.account.wooriaccount.dto.account.AccountDTO;
import io.woori.account.wooriaccount.dto.account.AccountRemittanceDTO;

import java.math.BigDecimal;


//TODO: DTO로 응답하기 
public interface AccountService {
	
	
	/*계좌 조회에 사용하는 메서드입니다.
	 * 
	 * @param accountNumber 조회하려는 계좌의 계좌번호
	 * @return 
	 * */
	AccountDTO AccountInquiry(String accountNumber);
	
	
	/* 송금(이체)에 사용하는 메서드입니다.
	 * 
	 * @param AccountRemittanceDTO
	 * @return return type 수정 
	 * */
	AccountDTO accountRemittance(AccountRemittanceDTO dto);


	/* 새로운 객체를 생성하는 메서드입니다.

	   @param customer 고객 정보 객체
	   @param initialDeposit 초기 입금액
	   @return 생성된 계좌의 정보를 담은 Account 객체
	 */
	AccountDTO accountCreate(Long customerId);
	
	
	/* 계좌 삭제에 사용하는 메서드입니다.
	 *
	 * @param accountNumber 삭제할 계좌의 계좌번호 입니다.
	 * @return 
	 * */
	AccountDTO accountDelete(String accountNumber);
}
