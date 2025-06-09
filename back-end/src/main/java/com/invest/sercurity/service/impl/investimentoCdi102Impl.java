package com.invest.sercurity.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.invest.entity.Carteira;
import com.invest.entity.dto.response.CDIResponseDTO;
import com.invest.entity.dto.response.ClientResponse;
import com.invest.reposiotries.CarteiraRepository;
import com.invest.service.CdiService;
import com.invest.service.ClientService;
import com.invest.service.investimentoCdi102;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class investimentoCdi102Impl implements investimentoCdi102 {

	private final CarteiraRepository repository;
	private final CdiService service;
	private final ClientService clientService;

	public void aplicarRendimentoDiario(UUID id) {
	    double taxa = cdi102(); // CDI * 1.02
	    BigDecimal taxaBD = BigDecimal.valueOf(taxa);

	    // Corrigindo aqui
	    Optional<ClientResponse> optionalClient = clientService.findById(id);
	    
	    if (optionalClient.isEmpty()) {
	    	return;
	    }

	    ClientResponse client = optionalClient.get();
	    
	    if (client.getCarteiras() == null) return;

	    for (Carteira carteira : client.getCarteiras()) {
	        BigDecimal saldo = carteira.getSaldo();
	        if (saldo == null) saldo = BigDecimal.ZERO;

	        BigDecimal rendimento = saldo.multiply(taxaBD);
	        BigDecimal novoSaldo = saldo.add(rendimento);

	        carteira.setSaldo(novoSaldo);
	        carteira.setDataUltimaAtualizacao(LocalDate.now());
	        carteira.setPercentual(taxa);

	        // Se tiver repositório, salve:
	         repository.save(carteira);
	    }
	}


	private double cdi102() {
		CDIResponseDTO cdi = service.foundCDI();
		double taxaCDI = cdi.getCdiDiario() / 100.0; // já é diário
		double percentualAplicacao = 1.02;
		return taxaCDI * percentualAplicacao;
	}

}
