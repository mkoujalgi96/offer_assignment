package com.kognitiv.exception;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
	
	private boolean status;
	private int errorCode;
	private final String errorMessage;
}
