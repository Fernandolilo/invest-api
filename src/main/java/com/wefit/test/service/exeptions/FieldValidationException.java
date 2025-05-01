package com.wefit.test.service.exeptions;

import java.util.List;

public class FieldValidationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final List<FieldMessage> errors;

	public FieldValidationException(List<FieldMessage> errors) {
		this.errors = errors;
	}

	public List<FieldMessage> getErrors() {
		return errors;
	}
}
