package com.wefit.test.controller.exceptions;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.wefit.test.service.exeptions.ApiErrors;
import com.wefit.test.service.exeptions.FieldMessage;
import com.wefit.test.service.exeptions.ObjectNotFoundException;

@RestControllerAdvice
public class ApplicationControllerAdvice {

	@ExceptionHandler(ObjectNotFoundException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErrors responseStatusException(ObjectNotFoundException ex) {
		return new ApiErrors(ex);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, Object> handleValidationErrors(MethodArgumentNotValidException ex) {
		List<Map<String, String>> errors = ex.getBindingResult().getFieldErrors().stream()
				.map(err -> Map.of("campo", err.getField(), "mensagem", err.getDefaultMessage()))
				.collect(Collectors.toList());

		return Map.of("erros", errors);
	}
}
