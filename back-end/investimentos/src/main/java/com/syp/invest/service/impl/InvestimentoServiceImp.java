package com.syp.invest.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.syp.invest.entity.Conta;
import com.syp.invest.entity.Investimento;
import com.syp.invest.entity.dto.ContaDTO;
import com.syp.invest.entity.dto.ContaUpdateDTO;
import com.syp.invest.entity.dto.InvestimentoNewDTO;
import com.syp.invest.repositories.InvestimentoRepository;
import com.syp.invest.service.ContaService;
import com.syp.invest.service.InvestimentoService;
import com.syp.invest.service.exceptions.ObjectNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class InvestimentoServiceImp implements InvestimentoService {

    private final ModelMapper mapper;
    private final InvestimentoRepository repository;
    private final ContaService contaService;

    @Override
    public Investimento save(InvestimentoNewDTO obj) {
        // Buscar conta pelo contaId
        ContaDTO contaDTO = contaService.findConta(obj.getContaId());

        if (contaDTO == null) {
            throw new ObjectNotFoundException(
                "Cliente não encontrado: " + obj.getCpfOuCnpj()
            );
        }

        Conta contaSelecionada = mapper.map(contaDTO, Conta.class);

        BigDecimal saldoConta = contaSelecionada.getSaldo();
        if (saldoConta == null) saldoConta = BigDecimal.ZERO;

        if (saldoConta.compareTo(obj.getValor()) < 0) {
            throw new ObjectNotFoundException("Saldo insuficiente para realizar o investimento.");
        }

        Investimento investimento = mapper.map(obj, Investimento.class);
        investimento.setId(null); // Garante que o Hibernate crie um novo
        investimento.setConta(contaSelecionada);
        investimento.setInstante(LocalDate.now());

        repository.save(investimento);

        BigDecimal novoSaldo = saldoConta.subtract(obj.getValor());
        ContaUpdateDTO contaUpdateDTO = mapper.map(contaSelecionada, ContaUpdateDTO.class);
        contaUpdateDTO.setSaldo(novoSaldo);
        contaService.update(contaUpdateDTO);

        return investimento;
    }
}
