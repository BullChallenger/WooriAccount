package io.woori.account.wooriaccount.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import javax.persistence.Temporal;
import java.util.List;


@Getter
@RequiredArgsConstructor(staticName = "of")
public class ResponseDTO<T> {

    private final HttpStatus httpStatus;
    private final String message;
    private final T data;



}



