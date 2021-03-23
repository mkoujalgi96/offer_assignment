package com.kognitiv.service;

import com.kognitiv.dto.OfferResponse;
import com.kognitiv.exception.ApiException;
import com.kognitiv.request.OfferRequest;

public interface AssignmentService {

	void createOffer(OfferRequest offerRequest) throws ApiException;

	OfferResponse getOffer(int pageSize, int pageNumber);

}
