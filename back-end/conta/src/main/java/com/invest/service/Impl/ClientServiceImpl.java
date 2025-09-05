package com.invest.service.Impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.invest.dto.ClientDTO;
import com.invest.dto.ClientNewDTO;
import com.invest.dto.ContaNewDTO;
import com.invest.dto.EnderecoNewDTO;
import com.invest.entity.Client;
import com.invest.entity.Conta;
import com.invest.entity.dto.response.ClientResponse;
import com.invest.entity.enums.Role;
import com.invest.infraestrutura.ContaMessageProducer;
import com.invest.reposiotries.ClientRepository;
import com.invest.sercurity.service.UserSecurityDetails;
import com.invest.service.ClientService;
import com.invest.service.ContaFactoryService;
import com.invest.service.ContaService;
import com.invest.service.EnderecoService;
import com.invest.service.ImageService;
import com.invest.service.UserService;
import com.invest.service.exeptions.ObjectNotFoundException;
import com.invest.service.exeptions.UserAccessNegativeException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ClientServiceImpl implements ClientService {

	private final ModelMapper mapper;
	private final ClientRepository clientRepository;
	private final EnderecoService enderecoService;
	private final UserService userService;
	private final ContaService contaService;
	private final ContaFactoryService contaFactoryService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final ImageService imageService;
	private final ContaMessageProducer contaMessageProducer;

	@Transactional
	@Override
	public ClientDTO save(ClientNewDTO cli, EnderecoNewDTO end) {
		Client entity = mapper.map(cli, Client.class);
		entity.setSenha(bCryptPasswordEncoder.encode(cli.getSenha()));
		entity.setRole(Role.USER);
		entity.addRole(Role.USER);

		imageService.processarSelfie(cli, entity);
		clientRepository.save(entity);

		enderecoService.salvarEnderecoParaCliente(end, entity);
		ClientDTO dto = mapper.map(entity, ClientDTO.class);

		ContaNewDTO contaDTO = contaFactoryService.criarContaParaCliente(dto);
		Conta contaSalva = contaService.save(contaDTO);
		contaDTO.setId(contaSalva.getId());
		contaDTO.setCpfOuCnpj(entity.getCpfOuCnpj());
		contaDTO.setNome(entity.getNome());
		contaDTO.setStatus(true);
		contaMessageProducer.sendMessage(contaDTO);
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
