package com.kognitiv.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.StringJoiner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kognitiv.constant.ErrorCode;
import com.kognitiv.dto.OfferResponse;
import com.kognitiv.dto.PlaceHolderDTO;
import com.kognitiv.entity.OfferEntity;
import com.kognitiv.entity.PropertyEntity;
import com.kognitiv.exception.ApiException;
import com.kognitiv.repository.OfferRepository;
import com.kognitiv.repository.PropertyRepository;
import com.kognitiv.request.Offer;
import com.kognitiv.request.OfferRequest;
import com.kognitiv.request.Property;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class AssignmentServiceImpl implements AssignmentService {

	@Autowired
	private OfferRepository offerRepository;

	@Autowired
	private PropertyRepository propertyRepository;

	@Autowired
	private RestTemplate restTemplate;

	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	public static final String EXCEPTION = "Exception: {}";

	public void createOffer(OfferRequest offerRequest) throws ApiException {

		Offer offer = offerRequest.getOffer();
		Property property = offer.getProperty();
		try {
			Date validFrom = simpleDateFormat.parse(offer.getValidFrom());
			Date validTill = simpleDateFormat.parse(offer.getValidTill());
			if (validFrom.after(validTill)) {
				throw new ApiException(HttpStatus.BAD_REQUEST, ErrorCode.INVALID_DATE_RANGE);
			}

			OfferEntity entity = new OfferEntity();
			entity.setName(offer.getName());
			entity.setValidFrom(validFrom);
			entity.setValidTill(validTill);
			entity.setLocation(offer.getLocation());

			String url = "https://jsonplaceholder.typicode.com/photos";
			ResponseEntity<String> placeHolderImages = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
			List<PlaceHolderDTO> images = new ObjectMapper().readValue(placeHolderImages.getBody(),
					new TypeReference<List<PlaceHolderDTO>>() {
					});
			StringJoiner joiner = new StringJoiner(",");
			joiner.add(images.get(new Random().nextInt(images.size())).getUrl());
			joiner.add(images.get(new Random().nextInt(images.size())).getUrl());
			entity.setImages(joiner.toString());

			PropertyEntity propertyEntity = new PropertyEntity();
			propertyEntity.setPropertyCode(property.getPropertyCode());
			propertyEntity.setImages(joiner.toString());
			propertyEntity.setCity(property.getCity());
			propertyEntity.setCountry(property.getCountry());
			propertyEntity.setDescription(property.getDescription());
			propertyEntity.setLattitude(property.getLattitude());
			propertyEntity.setLongitude(property.getLongitude());
			propertyEntity.setLanguages(property.getLanguages());
			propertyEntity.setTrustYouName(property.getTrustYouName());
			propertyEntity.setTrustYouScore(property.getTrustYouScore());
			propertyEntity.setTrustYouReviewsCount(property.getTrustYouReviewsCount());
			propertyEntity.setTrustYouScoreDescription(property.getTrustYouScoreDescription());
			propertyEntity.setTrustYouSourceCount(property.getTrustYouSourceCount());
			List<String> alternatePropertyCodes = Arrays.asList(property.getAlternatePropertyCodes().split(","));
			List<PropertyEntity> alternateProperties = propertyRepository.findByPropertyCodeIn(alternatePropertyCodes);

			propertyEntity.setAlternateProperties(alternateProperties);
			propertyRepository.save(propertyEntity);
			entity.setProperty(propertyEntity);
			offerRepository.save(entity);
		} catch (ParseException e) {
			log.error(EXCEPTION, e.getMessage());
			throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.DATE_PARSING);
		} catch (JsonProcessingException e) {
			log.error(EXCEPTION, e.getMessage());
			throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.JSON_PROCESSING);
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			log.error(EXCEPTION, e.getMessage());
			throw new ApiException(e.getStatusCode(), ErrorCode.REST_CLIENT);
		}
	}

	@Override
	public OfferResponse getOffer(int pageSize, int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber-1, pageSize);
		List<PropertyEntity> properties = propertyRepository.findAll(pageable).getContent();
		OfferResponse offerResponse = new OfferResponse();
		offerResponse.setProperties(properties);
		offerResponse.setPageSize(pageSize);
		offerResponse.setPageOffset(pageNumber);
		offerResponse.setTotalMatchedProperties(propertyRepository.count());
		
		return offerResponse;
	}

}
