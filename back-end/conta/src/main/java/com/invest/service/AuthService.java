package com.invest.service;

import com.invest.dto.AuthenticationDTO;

public interface AuthService {
	
	public String fromAuthentication(AuthenticationDTO auth);
	
	public String fromAuthorization();


}
