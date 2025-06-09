package com.invest.service.Impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.invest.dto.InvestimentoNewDTO;
import com.invest.entity.Client;
import com.invest.entity.Conta;
import com.invest.entity.Investimento;
import com.invest.reposiotries.ClientRepository;
import com.invest.reposiotries.InvestimentoRepository;
import com.invest.service.InvestimentoService;
import com.invest.service.exeptions.ObjectNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class InvestimentoServiceImpl implements InvestimentoService {

	private final InvestimentoRepository repository;
	private final ClientRepository clientRepository;
	private final ModelMapper mapper;

	@Override
	public Investimento save(InvestimentoNewDTO obj) {

		Optional<Client> clientOpt = clientRepository.findByCpfOuCnpj(obj.getCpfOuCnpj());

		if (clientOpt.isEmpty()) {
			throw new ObjectNotFoundException("Cliente não encontrado: " + obj.getCpfOuCnpj());
		}

		List<Conta> contas = clientOpt.get().getContas();

		double saldoTotal = contas.stream().mapToDouble(Conta::getSaldo).sum();

		InvestimentoNewDTO newInvest = InvestimentoNewDTO.builder().cpfOuCnpj(obj.getCpfOuCnpj()).valor(obj.getValor())
				.build();
		Investimento inv = mapper.map(newInvest, Investimento.class);

		if (saldoTotal < newInvest.getValor()) {
			throw new ObjectNotFoundException("Chegue seu saldo, não há valor suficiente: ");
		}

		repository.save(inv);
		// TODO Auto-generated method stub
		return inv;
	}

}
