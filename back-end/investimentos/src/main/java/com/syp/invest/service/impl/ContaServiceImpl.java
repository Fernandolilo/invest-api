package com.syp.invest.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.syp.invest.entity.Conta;
import com.syp.invest.entity.dto.ContaDTO;
import com.syp.invest.entity.dto.ContaUpdateDTO;
import com.syp.invest.repositories.ContaRepository;
import com.syp.invest.service.ContaService;
import com.syp.invest.service.exceptions.ObjectNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContaServiceImpl implements ContaService {

	private final ModelMapper mapper;
	private final ContaRepository repository;

	@Override
	public ContaDTO findConta(UUID id) {
		Conta conta = repository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException(String.format("Conta não existe ID: %s", id)));

		return mapper.map(conta, ContaDTO.class);
	}

	@Override
	public List<ContaDTO> foundConta(UUID id) {
		Optional<Conta> contas = repository.findById(id);

		if (contas.isEmpty()) {
			throw new ObjectNotFoundException("Nenhuma conta encontrada");
		}

		// Mapeia cada Conta para ContaDTO
		return contas.stream().map(conta -> {
			ContaDTO dto = mapper.map(conta, ContaDTO.class);
			return dto;
		}).toList();
	}

	@Override
	public Conta update(ContaUpdateDTO obj) {
		Optional<Conta> conta = repository.findById(obj.getId());

		if (conta.isEmpty()) {
			throw new ObjectNotFoundException("Conta não encontrado: " + obj.getId());
		}
		Conta con = mapper.map(obj, Conta.class);
		con.setNome(conta.get().getNome());
		con.setCpfOuCnpj(conta.get().getCpfOuCnpj());
		return repository.save(con);
	}

}