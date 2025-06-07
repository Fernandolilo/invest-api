package com.invest.sercurity.service.impl;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.invest.sercurity.service.UserSecurityDetails;
import com.invest.service.UserService;
import com.invest.service.exeptions.UserAccessNegativeException;

@Service
public class UserServiceImpl implements UserService{

	@Override
	public UserSecurityDetails authenticated() {
		  try {
	            return (UserSecurityDetails)
	                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	        } catch (Exception e) {
	            throw new UserAccessNegativeException("Sem permissão");
	        }
	}

}
