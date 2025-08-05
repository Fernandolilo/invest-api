package com.invest.service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.invest.entity.Client;
import com.invest.entity.Investimento;
import com.invest.reposiotries.ClientRepository;
import com.invest.service.CarteiraService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CarteiraServiceImpl implements CarteiraService {

	private final ClientRepository clientRepository;

	@Override
	public List<Investimento> investimentos(String cpf) {
		return clientRepository.findByCpfOuCnpj(cpf)
				.map(Client::getContas).stream().flatMap(List::stream)
				.filter(conta -> conta.getInvestimentos() != null)
				.flatMap(conta -> conta.getInvestimentos().stream())
				.collect(Collectors.toList());
	}

}
