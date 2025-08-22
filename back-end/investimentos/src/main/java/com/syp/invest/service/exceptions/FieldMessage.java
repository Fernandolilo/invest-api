package com.syp.invest.service.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FieldMessage extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private String fieldName;
	private String message;

		
}
