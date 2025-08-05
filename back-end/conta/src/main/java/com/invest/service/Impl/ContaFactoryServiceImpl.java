package com.invest.service.Impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.invest.dto.ClientDTO;
import com.invest.dto.ContaNewDTO;
import com.invest.entity.enums.TipoConta;
import com.invest.reposiotries.ContaRepository;
import com.invest.service.ContaFactoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContaFactoryServiceImpl implements ContaFactoryService {

	private final ContaRepository contaRepository;


    @Override
    public ContaNewDTO criarContaParaCliente(ClientDTO clientDTO) {
        Integer nextConta = contaRepository.findMaxConta().orElse(0) + 1;
        return ContaNewDTO.builder()
                .agencia(1000)
                .banco(1)
                .numero(nextConta)
                .cpf(clientDTO.getCpfOuCnpj())
                .saldo(BigDecimal.ZERO)
                .tipo(TipoConta.CONTA_CORRENTE)
                .build();
    }
}

