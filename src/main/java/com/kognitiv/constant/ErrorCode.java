package com.kognitiv.constant;

import lombok.Getter;

@Getter
public enum ErrorCode {

	INVALID_DATE_RANGE(1, "Invalid offer validity date range"),
	DATE_PARSING(2, "Date parsing exception"),
	JSON_PROCESSING(3,"Error while parsing json"), 
	REST_CLIENT(4,"Rest client exception");

	private final int code;

	private String description;

	private ErrorCode(int code, String description) {
		this.code = code;
		this.description = description;
	}
}
