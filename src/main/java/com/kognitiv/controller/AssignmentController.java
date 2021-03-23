package com.kognitiv.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kognitiv.dto.APIResponse;
import com.kognitiv.dto.OfferResponse;
import com.kognitiv.exception.ApiException;
import com.kognitiv.request.OfferRequest;
import com.kognitiv.service.AssignmentService;

@RestController
@RequestMapping("/collect")
public class AssignmentController {

	@Autowired
	private AssignmentService service;

	@PostMapping("/offer")
	public ResponseEntity<APIResponse> createOffer(@Valid @RequestBody OfferRequest offerRequest) throws ApiException {
		service.createOffer(offerRequest);
		APIResponse response = new APIResponse();
		response.setSuccess(true);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping("/offer")
	public ResponseEntity<APIResponse> getOffer(@RequestParam(name = "size") int pageSize,
			@RequestParam(name = "page") int pageNumber) throws ApiException {
		OfferResponse offers = service.getOffer(pageSize,pageNumber);
		APIResponse response = new APIResponse();
		response.setSuccess(true);
		response.setData(offers);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
