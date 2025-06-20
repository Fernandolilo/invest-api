package com.invest.service.Impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.invest.dto.ContaUpdateDTO;
import com.invest.dto.InvestimentoNewDTO;
import com.invest.entity.Client;
import com.invest.entity.Conta;
import com.invest.entity.Investimento;
import com.invest.reposiotries.ClientRepository;
import com.invest.reposiotries.InvestimentoRepository;
import com.invest.service.ContaService;
import com.invest.service.InvestimentoService;
import com.invest.service.exeptions.ObjectNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class InvestimentoServiceImpl implements InvestimentoService {

	private final InvestimentoRepository repository;
	private final ClientRepository clientRepository;
	private final ModelMapper mapper;
	private final ContaService contaService;

	@Override
	public Investimento save(InvestimentoNewDTO obj) {

		Optional<Client> clientOpt = clientRepository.findByCpfOuCnpj(obj.getCpfOuCnpj());

		if (clientOpt.isEmpty()) {
			throw new ObjectNotFoundException("Cliente não encontrado: " + obj.getCpfOuCnpj());
		}

		List<Conta> contas = clientOpt.get().getContas();

		//double saldoTotal = contas.stream().mapToDouble(Conta::getSaldo).sum();

		Conta contaSelecionada = contas.stream().filter(c -> c.getId().equals(obj.getConta())).findFirst()
				.orElseThrow(() -> new ObjectNotFoundException("Conta não encontrada ou não pertence ao cliente"));
		
		InvestimentoNewDTO newInvest = InvestimentoNewDTO.builder().cpfOuCnpj(obj.getCpfOuCnpj()).valor(obj.getValor())
				.build();
		
		double sdt = contaSelecionada.getSaldo();

		Investimento inv = mapper.map(newInvest, Investimento.class);
		inv.setConta(contaSelecionada);

		if (sdt < newInvest.getValor()) {
			throw new ObjectNotFoundException("Chegue seu saldo, não há valor suficiente: ");

		}

		repository.save(inv);
		// TODO Auto-generated method stub
		ContaUpdateDTO contaUpdateDTO = mapper.map(contaSelecionada, ContaUpdateDTO.class);
		
		double newSaldo = 0.0;
		newSaldo = contaSelecionada.getSaldo() - obj.getValor();
		
		contaUpdateDTO.setCpf(contaSelecionada.getClient().getCpfOuCnpj());
		contaUpdateDTO.setSaldo(newSaldo);		
		contaService.update(contaUpdateDTO);
		return inv;
	}

}
