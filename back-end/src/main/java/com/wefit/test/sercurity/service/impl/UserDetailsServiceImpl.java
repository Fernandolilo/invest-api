package com.wefit.test.sercurity.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.wefit.test.entity.Client;
import com.wefit.test.reposiotries.ClientRepository;
import com.wefit.test.sercurity.service.UserSecurityDetails;
import com.wefit.test.service.exeptions.AuthorizationException;


@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private ClientRepository repository;
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Client> cli = repository.findByEmail(username);

		if (!cli.isPresent()) {
			cli.orElseThrow(() -> new AuthorizationException("Client não encontrado" + "EMAIL: " + username));
		}
				
		return new UserSecurityDetails(cli.get().getId(), cli.get().getEmail(), cli.get().getSenha(), cli.get().getRoles());
	}

}
