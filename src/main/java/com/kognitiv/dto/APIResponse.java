package com.kognitiv.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_NULL)
public class APIResponse {

	private boolean success;
	private OfferResponse data;
	private String error;
}
