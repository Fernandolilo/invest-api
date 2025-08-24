package com.syp.invest.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.syp.invest.entity.CategoriaInvestimento;
import com.syp.invest.entity.Conta;
import com.syp.invest.entity.Investimento;
import com.syp.invest.entity.dto.ContaDTO;
import com.syp.invest.entity.dto.ContaUpdateDTO;
import com.syp.invest.entity.dto.InvestimentoNewDTO;
import com.syp.invest.repositories.CategoriaInvestimentosRepository;
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
    private final CategoriaInvestimentosRepository categoriaInvestimentosRepository;

    @Override
    public Investimento save(InvestimentoNewDTO obj) {
    	
        // Buscar categoria
        CategoriaInvestimento categoriaInvestimento = categoriaInvestimentosRepository.findById(obj.getCategoriaId())
                .orElseThrow(() -> new ObjectNotFoundException("Produto não encontrado: " + obj.getCategoriaId()));
    	
        // Buscar conta
        ContaDTO contaDTO = contaService.findConta(obj.getContaId());
        if (contaDTO == null) {
            throw new ObjectNotFoundException("Cliente não encontrado: " + obj.getCpfOuCnpj());
        }


        if (obj.getValor().compareTo(BigDecimal.ZERO) <= 0) {
        	throw new ObjectNotFoundException("Valor minimo da aplicacao: " + 1);
        }
        
        // Converter contaDTO -> Conta
        Conta contaSelecionada = mapper.map(contaDTO, Conta.class);
 

        // Validar saldo
        BigDecimal saldoConta = contaSelecionada.getSaldo() != null ? contaSelecionada.getSaldo() : BigDecimal.ZERO;
        if (saldoConta.compareTo(obj.getValor()) < 0) {
            throw new ObjectNotFoundException("Saldo insuficiente para realizar o investimento.");
        }

        // Mapear campos básicos do investimento
        
       
        Investimento investimento = Investimento.builder()
        		.categoria(categoriaInvestimento)
        		.conta(contaSelecionada)
        		.instante(LocalDate.now())
        		.valor(obj.getValor())
        		.build();
        		
        // Salvar investimento
        repository.save(investimento);

        // Atualizar saldo da conta
        BigDecimal novoSaldo = saldoConta.subtract(obj.getValor());
        ContaUpdateDTO contaUpdateDTO = mapper.map(contaSelecionada, ContaUpdateDTO.class);
        contaUpdateDTO.setSaldo(novoSaldo);
        contaService.update(contaUpdateDTO);

        return investimento;
    }

   

}
