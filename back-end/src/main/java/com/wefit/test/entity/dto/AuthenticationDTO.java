package com.wefit.test.entity.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class AuthenticationDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String email;
	private String password;
}
