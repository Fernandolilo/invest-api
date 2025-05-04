package com.wefit.test.service.Impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.wefit.test.entity.Client;
import com.wefit.test.entity.Endereco;
import com.wefit.test.entity.dto.AuthenticationDTO;
import com.wefit.test.entity.dto.ClientDTO;
import com.wefit.test.entity.dto.ClientNewDTO;
import com.wefit.test.entity.dto.EnderecoNewDTO;
import com.wefit.test.entity.dto.response.ClientResponse;
import com.wefit.test.entity.enums.Role;
import com.wefit.test.reposiotries.ClientRepository;
import com.wefit.test.reposiotries.EnderecoRepository;
import com.wefit.test.sercurity.jwt.JwtService;
import com.wefit.test.sercurity.service.UserSecurityDetails;
import com.wefit.test.service.ClientService;
import com.wefit.test.service.UserService;
import com.wefit.test.service.exeptions.AuthorizationException;
import com.wefit.test.service.exeptions.ObjectNotFoundException;
import com.wefit.test.service.exeptions.UserAccessNegativeException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ClientServiceImpl implements ClientService {

	private final ModelMapper mapper;
	private final ClientRepository clientRepository;
	private final EnderecoRepository enderecoRepository;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final UserService userService;


	@Override
	public ClientDTO save(ClientNewDTO cli, EnderecoNewDTO end) {
		
		UserSecurityDetails user = userService.authenticated();
		if (!user.hasRole( Role.ADMIN)) {
			throw new UserAccessNegativeException("Acesso negado");
		}

		Client entity = mapper.map(cli, Client.class);
		Endereco endereco = mapper.map(end, Endereco.class);

		clientRepository.save(entity);
		entity.addRole(cli.getRole());
		endereco.setClient(entity);
	
		enderecoRepository.save(endereco);

		ClientDTO dto = mapper.map(entity, ClientDTO.class);

		return dto;
	}

	@Override
	public Optional<ClientResponse> findById(UUID id) {
		return Optional.ofNullable(
				clientRepository.findById(id).map(client -> mapper.map(client, ClientResponse.class)).orElseThrow(
						() -> new ObjectNotFoundException(String.format("Cliente não encontrado com o ID: " + id))));
	}

	@Override
	public String fromAuthentication(AuthenticationDTO auth) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(auth.getEmail(), auth.getPassword()));
		if (authentication.isAuthenticated()) {
			String token = jwtService.generateToken(auth.getEmail());
			return token;
		} else {
			throw new AuthorizationException("EMAIL/SENHA invalido");
		}

	}

	private boolean hasFullAccess(UserSecurityDetails user) {
		// Lista de papéis permitidos
		List<Role> allowedRoles = Arrays.asList( Role.ADMIN, Role.USER);
		// Verifica se o usuário possui pelo menos um dos papéis permitidos
		return allowedRoles.stream().anyMatch(user::hasRole);
	}

}
