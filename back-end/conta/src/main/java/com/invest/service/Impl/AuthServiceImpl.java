package com.invest.service.Impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.invest.dto.AuthenticationDTO;
import com.invest.entity.enums.Role;
import com.invest.sercurity.jwt.JwtService;
import com.invest.sercurity.service.UserSecurityDetails;
import com.invest.service.AuthService;
import com.invest.service.UserService;
import com.invest.service.exeptions.AuthorizationException;
import com.invest.service.exeptions.UserAccessNegativeException;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
	
	private final AuthenticationManager authenticationManager;
	private final UserService userService;
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

	@Override
	public String fromAuthorization() {
	    UserSecurityDetails user = userService.authenticated();
	    if (!hasFullAccess(user)) {
	        throw new UserAccessNegativeException("Acesso negado");
	    }
	    
	    return user.getAuthorities()
	               .stream()
	               .map(Object::toString)
	               .collect(Collectors.joining(", "));
	}
	
	
	private boolean hasFullAccess(UserSecurityDetails user) {
		// Lista de papéis permitidos
		List<Role> allowedRoles = Arrays.asList(Role.ADMIN, Role.USER);
		// Verifica se o usuário possui pelo menos um dos papéis permitidos
		return allowedRoles.stream().anyMatch(user::hasRole);
	}
}
