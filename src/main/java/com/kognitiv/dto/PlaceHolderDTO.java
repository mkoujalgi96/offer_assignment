package com.kognitiv.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaceHolderDTO {

	private String albumId;
	private String id;
	private String title;
	private String url;
	private String thumbnailUrl;
}
