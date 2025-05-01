package com.wefit.test.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.wefit.test.service.exeptions.ApiErrors;
import com.wefit.test.service.exeptions.ObjectNotFoundException;

@RestControllerAdvice
public class ApplicationControllerAdvice {

	@ExceptionHandler(ObjectNotFoundException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErrors responseStatusException(ObjectNotFoundException ex) {
		return new ApiErrors(ex);
	}
}
