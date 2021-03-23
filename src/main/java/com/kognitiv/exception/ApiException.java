package com.kognitiv.exception;

import org.springframework.http.HttpStatus;

import com.kognitiv.constant.ErrorCode;

import lombok.Getter;

@Getter
public class ApiException extends Exception {

	private static final long serialVersionUID = 1L;
	private final HttpStatus status;
	private final ErrorCode errorCode;
	private String errorMessage;

	public ApiException(HttpStatus status, ErrorCode errorCode) {
		super(errorCode.getDescription());
		this.status = status;
		this.errorCode = errorCode;
		this.errorMessage = errorCode.getDescription();
	}
}
