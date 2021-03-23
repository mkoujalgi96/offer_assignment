package com.kognitiv.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.kognitiv.dto.APIResponse;
import com.kognitiv.dto.OfferResponse;
import com.kognitiv.exception.ApiException;
import com.kognitiv.request.OfferRequest;
import com.kognitiv.service.AssignmentService;

@ExtendWith(SpringExtension.class)
public class AssignmentControllerTest {

	@Mock
	private AssignmentService service;

	@InjectMocks
	private AssignmentController controller;
	
	@Test
	void createOfferTest() throws ApiException {
		doNothing().when(service).createOffer(Mockito.any());
		ResponseEntity<APIResponse> response = controller.createOffer(new OfferRequest());
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(response.getBody());
	}
	
	@Test
	void getOfferTest() throws ApiException {
		when(service.getOffer(Mockito.anyInt(), Mockito.anyInt())).thenReturn(new OfferResponse());
		ResponseEntity<APIResponse> response = controller.getOffer(1, 1);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
	}
}
