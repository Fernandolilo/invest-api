package com.invest.service.Impl;

import java.beans.Transient;
import java.math.BigDecimal;
import java.time.YearMonth;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.invest.dto.CartaoDTO;
import com.invest.dto.CartaoNewDTO;
import com.invest.dto.ContaDTO;
import com.invest.entity.Cartao;
import com.invest.entity.Conta;
import com.invest.entity.enums.BandeiraCartao;
import com.invest.entity.enums.TipoCartao;
import com.invest.reposiotries.CartaoRepository;
import com.invest.service.CartaoService;
import com.invest.service.ContaService;
import com.invest.service.GenereteCartaoService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartaoServiceImpl implements CartaoService{
	
	private final GenereteCartaoService genereteCartaoService;
	private final CartaoRepository repository;
	private final ModelMapper mapper;
	private final ContaService contaService;

	
	@Transactional
	@Override
	public CartaoDTO save(CartaoNewDTO obj) {

		TipoCartao tipo = (obj.getTipo() == null) ? TipoCartao.DEBITO : obj.getTipo();
		BandeiraCartao tipoBand = (obj.getBandeira() == null) ? BandeiraCartao.VISA : obj.getBandeira();

	    String senhaCard = obj.getSenha();

	    ContaDTO contaDTO = contaService.findById(obj.getContaId());
	    Conta conta = mapper.map(contaDTO, Conta.class);

	    Cartao novoCartao = Cartao.builder()
	            .numero(genereteCartaoService.gerarNumeroCartao(tipoBand))
	            .nomeTitular(obj.getNomeTitular())
	            .validade(YearMonth.now().plusYears(3).atEndOfMonth())
	            .cvv(gerarCVV())
	            .tipo(tipo)
	            .bandeira(tipoBand)
	            .limite(BigDecimal.valueOf(500))
	            .senha(senhaCard)
	            .conta(conta)
	            .build();

	    Cartao cartao = repository.save(novoCartao);

	    return mapper.map(cartao, CartaoDTO.class);
	}

	
	

	private String gerarCVV() {
	    int cvv = (int) (Math.random() * 900) + 100;
	    return String.valueOf(cvv);
	}

}
