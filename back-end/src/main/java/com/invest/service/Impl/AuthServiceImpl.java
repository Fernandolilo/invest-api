package com.invest.service.Impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.invest.dto.AuthenticationDTO;
import com.invest.sercurity.jwt.JwtService;
import com.invest.service.AuthService;
import com.invest.service.exeptions.AuthorizationException;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
	
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;

	@Override
	public String fromAuthentication(AuthenticationDTO auth) {
		try {
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(auth.getEmail(), auth.getPassword()));

			return jwtService.generateToken(auth.getEmail());

		} catch (BadCredentialsException | UsernameNotFoundException ex) {
			throw new AuthorizationException("Email ou senha inválidos");
		}
	}
}
