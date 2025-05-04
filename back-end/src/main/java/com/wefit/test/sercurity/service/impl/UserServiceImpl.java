package com.wefit.test.sercurity.service.impl;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.wefit.test.sercurity.service.UserSecurityDetails;
import com.wefit.test.service.UserService;
import com.wefit.test.service.exeptions.UserAccessNegativeException;

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
