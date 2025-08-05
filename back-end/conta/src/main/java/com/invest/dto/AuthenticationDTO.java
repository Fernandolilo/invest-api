package com.invest.dto;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthenticationDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String email;
	private String password;
}
