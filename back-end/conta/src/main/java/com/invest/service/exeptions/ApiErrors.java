package com.invest.service.exeptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.validation.BindingResult;

public class ApiErrors {

	private List<String> errors;

	public ApiErrors(BindingResult bindingResult) {
		this.errors = new ArrayList<>();
		bindingResult.getAllErrors().forEach(error -> this.errors.add(error.getDefaultMessage()));

	}

	
	public ApiErrors(ObjectNotFoundException ex) {
	    this.errors = List.of(ex.getMessage());
	}

	public ApiErrors(AuthorizationException ex) {
	    this.errors = List.of(ex.getMessage());
	}

	public ApiErrors(FieldValidationException ex) {
		ex.getStackTrace();
		this.errors = Arrays.asList(ex.getMessage());
	}

	public ApiErrors(FieldMessage ex) {
		ex.getStackTrace();
		this.errors = Arrays.asList(ex.getMessage());
	}

	public List<String> getErrors() {
		return errors;
	}
}