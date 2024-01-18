package io.woori.account.wooriaccount.common.dto;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class ResponseDTO<T> {

	private final HttpStatus httpStatus;
	private final String message;
	private final T data;

}



