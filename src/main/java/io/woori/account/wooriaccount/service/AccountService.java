package io.woori.account.wooriaccount.service;

import io.woori.account.wooriaccount.domain.entity.Account;


//TODO: DTO로 응답하기 
public interface AccountService {
	
	
	/*계좌 조회에 사용하는 메서드입니다.
	 * 
	 * @param accountNumber 조회하려는 계좌의 계좌번호
	 * @return 
	 * */
	Account AccountInquiry(String accountNumber);
	
	
	/* 송금에 사용하는 메서드입니다.
	 * 
	 * @param accountNumber 송금을 하는 주체의 계좌번호
	 * @param targetAccountNumber 송금을 받는 주체의 계좌번호
	 * @return return type 수정 
	 * */
	void accountRemittance(String accountNumber, String targetAccountNumber);

	/* 이체(출금)에 사용하는 메서드입니다.
	 * 
	 * @param accountNumber - 돈을 보내는 주체의 계좌번호
	 * @param targetAccountNumber 이체를 받는 주체의 계좌번호
	 * @return return type 수정
	 * */
	void accountWithdraw(String accountNumber, String targetAccountNumber);
	
}
