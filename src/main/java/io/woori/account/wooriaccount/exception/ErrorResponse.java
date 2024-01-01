package io.woori.account.wooriaccount.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {

    private String message;
    private String code;
    private int status;

    public ErrorResponse(ErrorCode code){
        this.message = code.getMessage();
        this.code = code.getCode();
        this.status = code.getStatus();
    }

}
