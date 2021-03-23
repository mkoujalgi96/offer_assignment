package com.kognitiv.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.kognitiv.constant.ErrorCode;
import com.kognitiv.entity.PropertyEntity;
import com.kognitiv.exception.ApiException;
import com.kognitiv.repository.OfferRepository;
import com.kognitiv.repository.PropertyRepository;
import com.kognitiv.request.Offer;
import com.kognitiv.request.OfferRequest;
import com.kognitiv.request.Property;

@ExtendWith(SpringExtension.class)
public class AssignmentServiceImplTest {

	@InjectMocks
	private AssignmentServiceImpl service;

	@Mock
	private OfferRepository offerRepository;

	@Mock
	private PropertyRepository propertyRepository;

	@Mock
	private RestTemplate restTemplate;

	@Test
	void getOfferTest() {
		Page<PropertyEntity> res = Page.empty();
		when(propertyRepository.findAll(Mockito.any(Pageable.class))).thenReturn(res);
		assertNotNull(service.getOffer(1, 1));
	}

	@Test
	void createOfferParsingExceptionTest() throws ApiException {
		OfferRequest request = new OfferRequest();
		Offer offer = new Offer();
		offer.setValidFrom("2020/02-12");
		request.setOffer(offer);
		try {
			service.createOffer(request);
		} catch (ApiException e) {
			assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, e.getStatus());
			assertEquals(ErrorCode.DATE_PARSING, e.getErrorCode());
		}
	}

	@Test
	void createOfferInvalidDateRangeTest() throws ApiException {
		OfferRequest request = new OfferRequest();
		Offer offer = new Offer();
		offer.setValidFrom("2021-02-12");
		offer.setValidTill("2020-02-12");
		request.setOffer(offer);
		try {
			service.createOffer(request);
		} catch (ApiException e) {
			assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
			assertEquals(ErrorCode.INVALID_DATE_RANGE, e.getErrorCode());
		}
	}

	@Test
	void createOfferRestClientErrorTest() throws ApiException {
		OfferRequest request = new OfferRequest();
		Offer offer = new Offer();
		offer.setValidFrom("2020-01-12");
		offer.setValidTill("2020-02-12");
		request.setOffer(offer);
		when(restTemplate.exchange(Mockito.anyString(), Mockito.eq(HttpMethod.GET), Mockito.nullable(HttpEntity.class),
				Mockito.eq(String.class))).thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));
		try {
			service.createOffer(request);
		} catch (ApiException e) {
			assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, e.getStatus());
			assertEquals(ErrorCode.REST_CLIENT, e.getErrorCode());
		}
	}

	@Test
	void createOfferJsonProcessingExceptionTest() throws ApiException {
		OfferRequest request = new OfferRequest();
		Offer offer = new Offer();
		offer.setValidFrom("2020-01-12");
		offer.setValidTill("2020-02-12");
		request.setOffer(offer);
		when(restTemplate.exchange(Mockito.anyString(), Mockito.eq(HttpMethod.GET), Mockito.nullable(HttpEntity.class),
				Mockito.eq(String.class))).thenReturn(new ResponseEntity<String>("", HttpStatus.OK));
		try {
			service.createOffer(request);
		} catch (ApiException e) {
			assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, e.getStatus());
			assertEquals(ErrorCode.JSON_PROCESSING, e.getErrorCode());
		}
	}

	@Test
	void createOfferTest() throws ApiException {
		OfferRequest request = new OfferRequest();
		Offer offer = new Offer();
		offer.setValidFrom("2020-01-12");
		offer.setValidTill("2020-02-12");
		Property property = new Property();
		property.setAlternatePropertyCodes("");
		offer.setProperty(property);
		request.setOffer(offer);
		String images = "[\r\n" + "		  {\r\n" + "		    \"albumId\": 1,\r\n" + "		    \"id\": 1,\r\n"
				+ "		    \"title\": \"accusamus beatae ad facilis cum similique qui sunt\",\r\n"
				+ "		    \"url\": \"https://via.placeholder.com/600/92c952\",\r\n"
				+ "		    \"thumbnailUrl\": \"https://via.placeholder.com/150/92c952\"\r\n" + "		  },\r\n"
				+ "		  {\r\n" + "		    \"albumId\": 1,\r\n" + "		    \"id\": 2,\r\n"
				+ "		    \"title\": \"reprehenderit est deserunt velit ipsam\",\r\n"
				+ "		    \"url\": \"https://via.placeholder.com/600/771796\",\r\n"
				+ "		    \"thumbnailUrl\": \"https://via.placeholder.com/150/771796\"\r\n" + "		  }\r\n"
				+ "		  ]";

		when(restTemplate.exchange(Mockito.anyString(), Mockito.eq(HttpMethod.GET), Mockito.nullable(HttpEntity.class),
				Mockito.eq(String.class))).thenReturn(new ResponseEntity<String>(images, HttpStatus.OK));
		when(propertyRepository.findByPropertyCodeIn(Mockito.any())).thenReturn(null);
		when(propertyRepository.save(Mockito.any())).thenReturn(new PropertyEntity());
		service.createOffer(request);
	}
}
