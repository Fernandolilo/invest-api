package com.syp.invest.service;

import java.util.List;
import java.util.UUID;

import com.syp.invest.entity.Conta;
import com.syp.invest.entity.dto.ContaDTO;
import com.syp.invest.entity.dto.ContaUpdateDTO;

public interface ContaService {
	
	ContaDTO findConta (UUID id);
	
	public Conta update(ContaUpdateDTO conta);
	
	public List< ContaDTO> foundConta(UUID id);

}
