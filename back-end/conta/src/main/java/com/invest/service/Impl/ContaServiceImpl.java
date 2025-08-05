package com.invest.service.Impl;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.invest.dto.ContaDTO;
import com.invest.dto.ContaNewDTO;
import com.invest.dto.ContaUpdateDTO;
import com.invest.entity.Client;
import com.invest.entity.Conta;
import com.invest.entity.enums.Role;
import com.invest.reposiotries.ClientRepository;
import com.invest.reposiotries.ContaRepository;
import com.invest.sercurity.service.UserSecurityDetails;
import com.invest.service.ContaService;
import com.invest.service.UserService;
import com.invest.service.exeptions.ObjectNotFoundException;
import com.invest.service.exeptions.UserAccessNegativeException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ContaServiceImpl implements ContaService {

	private final ContaRepository repository;
	private final ModelMapper mapper;
	private final ClientRepository clienteRepository;
	private final UserService userService;
	
	@Override
	public Conta save(ContaNewDTO conta) {
		Optional<Client> clientOpt = clienteRepository.findByCpfOuCnpj(conta.getCpf());

		Optional<Integer> maxConta = repository.findMaxConta();
		Integer nextConta = maxConta.orElse(0) + 1;

		if (clientOpt.isEmpty()) {
			throw new ObjectNotFoundException("Cliente não encontrado: " + conta.getCpf());
		}

		Conta con = mapper.map(conta, Conta.class);
		con.setClient(clientOpt.get());
		con.setNumero(nextConta);

		return repository.save(con);
	}

	@Override
	public Conta update(ContaUpdateDTO obj) {
		Optional<Conta> conta = repository.findById(obj.getId());
		Optional<Client> clientOpt = clienteRepository.findByCpfOuCnpj(obj.getCpf());

		if (conta.isEmpty()) {
			throw new ObjectNotFoundException("Conta não encontrado: " + obj.getId());
		}

		Conta con = mapper.map(obj, Conta.class);
		con.setClient(clientOpt.get());
		return repository.save(con);
	}

	@Override
	public List<ContaDTO> foundConta() {
		UserSecurityDetails user = userService.authenticated();

		if (!hasFullAccess(user)) {
			throw new UserAccessNegativeException("Acesso negado");
		}

		List<Conta> contas = repository.findAllByClientId(user.getId());

		if (contas.isEmpty()) {
			throw new ObjectNotFoundException("Nenhuma conta encontrada");
		}

		// Mapeia cada Conta para ContaDTO
		return contas.stream().map(conta -> {
			ContaDTO dto = mapper.map(conta, ContaDTO.class);
			dto.setNome(conta.getClient().getNome());
			dto.setCpfOuCnpj(conta.getClient().getCpfOuCnpj());
			return dto;
		}).toList();
	}

	@Override
	public ContaDTO findById(UUID id) {
		UserSecurityDetails user = userService.authenticated();
		if (!hasFullAccess(user)) {
			throw new UserAccessNegativeException("Acesso negado");
		}

		Conta conta = repository.findById(id)
			.orElseThrow(() -> new ObjectNotFoundException("Conta não encontrado: " + id));

		ContaDTO dto = mapper.map(conta, ContaDTO.class);

		byte[] imagemBytes = conta.getClient().getImagem();
		if (imagemBytes != null) {
			String base64 = Base64.getEncoder().encodeToString(imagemBytes);
			String mimeType = conta.getClient().getImagemTipo(); // ex: "image/jpeg"
			dto.setSelfie("data:" + mimeType + ";base64," + base64);
		}

		dto.setNome(conta.getClient().getNome());

		return dto;
	}


	private boolean hasFullAccess(UserSecurityDetails user) {
		// Lista de papéis permitidos
		List<Role> allowedRoles = Arrays.asList(Role.ADMIN, Role.USER);
		// Verifica se o usuário possui pelo menos um dos papéis permitidos
		return allowedRoles.stream().anyMatch(user::hasRole);
	}

}
