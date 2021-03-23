package com.kognitiv.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.kognitiv.constant.ErrorCode;

@ExtendWith(SpringExtension.class)
class ApiExceptionHandlerTest {

	@InjectMocks
	ApiExceptionHandler apiExceptionHandler;

	@Test
	void handleCustomExceptionTest() {
		assertEquals(HttpStatus.BAD_REQUEST,
				apiExceptionHandler.handleCustomException(
						new ApiException(HttpStatus.BAD_REQUEST, ErrorCode.INVALID_DATE_RANGE))
						.getStatusCode());

	}

	@Test
	void handleValidationExceptionsTest() {
		BindingResult bindingResult = Mockito.mock(BindingResult.class);
		List<ObjectError> errors = new ArrayList<>();
		FieldError error = new FieldError("field", "objectName", "defaultMessage");
		errors.add(error);
		when(bindingResult.getAllErrors()).thenReturn(errors);
		MethodParameter parameter = Mockito.mock(MethodParameter.class);
		MethodArgumentNotValidException ex = new MethodArgumentNotValidException(parameter, bindingResult);
		assertEquals(HttpStatus.BAD_REQUEST, apiExceptionHandler.handleValidationExceptions(ex).getStatusCode());

	}
}
