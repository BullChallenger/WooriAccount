package io.woori.account.wooriaccount.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@JsonFormat
public enum ErrorCode implements EnumModel{

    //회원 로그인 시 발생 가능 예외
    INVALID_Customer_Login(401, "C001", "존재하지 않는 고객 정보입니다."),
    INVALID_Customer_Password(401, "C002", "비밀번호가 일치하지 않습니다."),

    //중복여부 체크
    DUPLICATE_CUSTOMER(400,"D001","중복된 이메일입니다."),

    //계좌 관련 예외
    ACCOUNT_NOT_FOUND(404, "A001", "계좌를 찾을 수 없습니다."),
    INSUFFICIENT_FUNDS(400, "A002", "잔액이 부족합니다."),
    INVALID_TRANSACTION(400, "A003", "유효하지 않은 거래입니다.");


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
