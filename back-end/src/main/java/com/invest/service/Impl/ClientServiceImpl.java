package com.invest.service.Impl;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.invest.dto.AuthenticationDTO;
import com.invest.dto.ClientDTO;
import com.invest.dto.ClientNewDTO;
import com.invest.dto.ContaNewDTO;
import com.invest.dto.EnderecoNewDTO;
import com.invest.entity.Client;
import com.invest.entity.Endereco;
import com.invest.entity.dto.response.ClientResponse;
import com.invest.entity.enums.Role;
import com.invest.entity.enums.TipoConta;
import com.invest.reposiotries.ClientRepository;
import com.invest.reposiotries.ContaRepository;
import com.invest.reposiotries.EnderecoRepository;
import com.invest.sercurity.jwt.JwtService;
import com.invest.sercurity.service.UserSecurityDetails;
import com.invest.service.ClientService;
import com.invest.service.ContaService;
import com.invest.service.UserService;
import com.invest.service.exeptions.AuthorizationException;
import com.invest.service.exeptions.ObjectNotFoundException;
import com.invest.service.exeptions.UserAccessNegativeException;

import jakarta.transaction.Transactional;
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
	private final ContaService contaService;
	private final ContaRepository contaRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Transactional
	@Override
	public ClientDTO save(ClientNewDTO cli, EnderecoNewDTO end) {
		Client entity = mapper.map(cli, Client.class);
		entity.setSenha(bCryptPasswordEncoder.encode(cli.getSenha()));
		entity.setRole(Role.USER);
		entity.addRole(Role.USER);

		// 👇 Tratamento da imagem base64 enviada
		if (cli.getSelfie() != null && cli.getSelfie().startsWith("data:")) {
		    String[] parts = cli.getSelfie().split(",");
		    if (parts.length == 2) {
		        String metadata = parts[0]; // ex: data:image/jpeg;base64
		        String base64Data = parts[1];

		        String tipoImagem = metadata.substring(metadata.indexOf(":") + 1, metadata.indexOf(";"));
		        byte[] imagemBytes = Base64.getDecoder().decode(base64Data);

		        entity.setImagem(imagemBytes);
		        entity.setImagemTipo(tipoImagem);
		        entity.setImagemNome("selfie_" + UUID.randomUUID() + "." + tipoImagem.split("/")[1]);
		    }
		}

		clientRepository.save(entity);

		Endereco endereco = mapper.map(end, Endereco.class);
		endereco.setClient(entity);
		enderecoRepository.save(endereco);

		ClientDTO dto = mapper.map(entity, ClientDTO.class);

		Optional<Integer> maxConta = contaRepository.findMaxConta();
		Integer nextConta = maxConta.orElse(0) + 1;

		ContaNewDTO contaNewDTO = ContaNewDTO.builder()
			.agencia(1000)
			.banco(1)
			.numero(nextConta)
			.cpf(dto.getCpfOuCnpj())
			.saldo(0)
			.tipo(TipoConta.CONTA_CORRENTE)
			.build();

		contaService.save(contaNewDTO);

		return dto;
	}

	@Override
	public Optional<ClientResponse> findById(UUID id) {

		UserSecurityDetails user = userService.authenticated();
		if (!hasFullAccess(user)) {
			throw new UserAccessNegativeException("Acesso negado");
		}

		return Optional.ofNullable(
				clientRepository.findById(id).map(client -> mapper.map(client, ClientResponse.class)).orElseThrow(
						() -> new ObjectNotFoundException(String.format("Cliente não encontrado com o ID: " + id))));
	}

	@Override
	public List<Client> findAll() {
		UserSecurityDetails user = userService.authenticated();
		if (!hasFullAccess(user)) {
			throw new UserAccessNegativeException("Acesso negado");
		}

		return clientRepository.findAll();
	}

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
	public Optional<ClientResponse> foundCli(String cpfOuCnpj) {
		Optional<Client> cli = clientRepository.findByCpfOuCnpj(cpfOuCnpj);

		// Se estiver presente, mapeia e retorna como Optional
		return cli.map(client -> mapper.map(client, ClientResponse.class));
	}
	
	private boolean hasFullAccess(UserSecurityDetails user) {
		// Lista de papéis permitidos
		List<Role> allowedRoles = Arrays.asList(Role.ADMIN, Role.USER);
		// Verifica se o usuário possui pelo menos um dos papéis permitidos
		return allowedRoles.stream().anyMatch(user::hasRole);
	}

}
