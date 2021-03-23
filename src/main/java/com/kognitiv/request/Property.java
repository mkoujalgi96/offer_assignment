package com.kognitiv.request;

import lombok.Data;

@Data
public class Property {

	private String propertyCode;
	private String languages;
	private String description;
	private String city;
	private String country;
	private double lattitude;
	private double longitude;

	// Trust you
	private String trustYouName;
	private String trustYouScore;
	private String trustYouScoreDescription;
	private long trustYouSourceCount;
	private long trustYouReviewsCount;

	private String alternatePropertyCodes;
}
