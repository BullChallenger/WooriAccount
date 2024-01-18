package io.woori.account.wooriaccount.account.service.inter;

import java.util.List;

import io.woori.account.wooriaccount.account.domain.dto.AccountAllDTO;
import io.woori.account.wooriaccount.account.domain.dto.AccountDTO;
import io.woori.account.wooriaccount.account.domain.dto.AccountRemittanceDTO;

//TODO: DTO로 응답하기
public interface AccountService {

	/*계좌 조회에 사용하는 메서드입니다.
	 *
	 * @param accountNumber 조회하려는 계좌의 계좌번호
	 * @return
	 * */
	AccountDTO accountInquiry(String accountNumber);

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

	/*
	 * 고객 pk로 계좌 정보를 모두 가져오는 메서드입니다.
	 *
	 * @param id 고객 pk
	 * @return 계좌 정보 dto
	 * */
	List<AccountAllDTO> findAllByCustomerId(Long id);
}
