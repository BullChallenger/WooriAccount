package io.woori.account.wooriaccount.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@JsonFormat
public enum ErrorCode implements EnumModel{
    // 에러코드는 해당 엔티티의 앞자리를 따서 만들기
    // Customer 400번대
    // Tx 300번대
    // Account 500번대

    //회원 로그인 시 발생 가능 예외
    INVALID_Customer_Login(401, "C001", "존재하지 않는 고객 정보입니다."),
    INVALID_Customer_Password(401, "C002", "비밀번호가 일치하지 않습니다."),

    //중복여부 체크
    DUPLICATE_CUSTOMER(400,"C003","중복된 이메일입니다."),

    // 해당 거래내역이 존재하지 않을 때 발생하는 예외
    NOT_FOUND_TX(301, "T001", "존재하지 않는 거래내역입니다."),

    //계좌 관련 예외
    ACCOUNT_NOT_FOUND(504, "A001", "계좌를 찾을 수 없습니다."),
    INSUFFICIENT_FUNDS(500, "A002", "잔액이 부족합니다."),
    INVALID_TRANSACTION(500, "A003", "유효하지 않은 거래입니다."),
	
    // 404 not fount Exception
    NOT_FOUND_CUSTOMER(404, "N001", "존재하지 않는 회원입니다."),
    NOT_FOUND_ACCOUNT(404, "N002", "존재하지 않는 계좌입니다.");
	
    private int status;
    private String code;
    private String message;

    ErrorCode(int status, String code, String message){
        this.status = status;
        this.code = code;
        this.message = message;

    }
    @Override
    public String getKey() {
        return null;
    }

    @Override
    public String getValue() {
        return null;
    }
}
