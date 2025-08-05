package com.invest.service;

import java.util.List;
import java.util.UUID;

import com.invest.dto.ContaDTO;
import com.invest.dto.ContaNewDTO;
import com.invest.dto.ContaTrasacaoDepSaqDTO;
import com.invest.dto.ContaUpdateDTO;
import com.invest.entity.Conta;

public interface ContaService {

	public Conta save(ContaNewDTO conta);
	
	public Conta update(ContaUpdateDTO conta);
	
	public List< ContaDTO> foundConta();
	
	public ContaDTO findById(UUID id);
	
	public Conta deposito(ContaTrasacaoDepSaqDTO conta);
			
	public Conta saque(ContaTrasacaoDepSaqDTO conta);
}
