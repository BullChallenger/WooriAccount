package io.woori.account.wooriaccount.common.exception;

public class TxHistoryException extends RuntimeException{

    private final ErrorCode errorCode;

    public TxHistoryException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode(){
        return errorCode;
    }

}
