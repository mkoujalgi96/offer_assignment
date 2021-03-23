package com.kognitiv.request;

import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Offer {

	@ApiModelProperty(notes = "Offer name")
	private String name;

	@ApiModelProperty(notes = "Offer validity start date")
	@Pattern(regexp = "([0-9]{4})-([0-9]{2})-([0-9]{2})", message = "Date pattern in invalid (YYYY-MM-dd)")
	private String validFrom;

	@ApiModelProperty(notes = "Offer validity end date")
	@Pattern(regexp = "([0-9]{4})-([0-9]{2})-([0-9]{2})", message = "Date pattern in invalid (YYYY-MM-dd)")
	private String validTill;

	@ApiModelProperty(notes = "Offer location")
	private String location;

	@ApiModelProperty(notes = "Property")
	private Property property;

}
