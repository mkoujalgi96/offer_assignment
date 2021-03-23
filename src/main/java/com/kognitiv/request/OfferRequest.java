package com.kognitiv.request;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OfferRequest {

	@JsonProperty("Offer")
	@ApiModelProperty(notes = "Offer details")
	@Valid
	private Offer offer;
	
}
