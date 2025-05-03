package com.wefit.test.sercurity.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.wefit.test.entity.Client;
import com.wefit.test.reposiotries.ClientRepository;
import com.wefit.test.sercurity.service.UserSecurityDetails;
import com.wefit.test.service.exeptions.AuthorizationException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	private final ClientRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Client> cli = repository.findByEmail(username);

		if (!cli.isPresent()) {
			cli.orElseThrow(() -> new AuthorizationException("Client não encontrado" + "EMAIL: " + username));
		}
		UserSecurityDetails user = UserSecurityDetails.builder()
				.id(cli.get().getId())
				.senha(cli.get().getSenha())
				.authorities(
			            List.of(new SimpleGrantedAuthority("ROLE_" + cli.get().getRole()))
			        )
				.email(cli.get().getEmail())
				.build();
		return user;
	}

}
