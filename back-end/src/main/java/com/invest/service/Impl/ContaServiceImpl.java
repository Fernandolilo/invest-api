package com.invest.service.Impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.invest.dto.ContaNewDTO;
import com.invest.dto.ContaUpdateDTO;
import com.invest.entity.Client;
import com.invest.entity.Conta;
import com.invest.reposiotries.ClientRepository;
import com.invest.reposiotries.ContaRepository;
import com.invest.service.ContaService;
import com.invest.service.exeptions.ObjectNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ContaServiceImpl implements ContaService {

	private final ContaRepository repository;
	private final ModelMapper mapper;
	private final ClientRepository clienteRepository;

	@Override
	public Conta save(ContaNewDTO conta) {
		Optional<Client> clientOpt = clienteRepository.findByCpfOuCnpj(conta.getCpf());

		if (clientOpt.isEmpty()) {
			throw new ObjectNotFoundException("Cliente não encontrado: " + conta.getCpf());
		}

		Conta con = mapper.map(conta, Conta.class);
		con.setClient(clientOpt.get());

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

}
