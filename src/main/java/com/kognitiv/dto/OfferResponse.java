package com.kognitiv.dto;

import java.util.List;

import com.kognitiv.entity.PropertyEntity;

import lombok.Data;

@Data
public class OfferResponse {
	private long totalMatchedProperties;
	List<PropertyEntity> properties;
	private int pageSize;
	private int pageOffset;
	
}
