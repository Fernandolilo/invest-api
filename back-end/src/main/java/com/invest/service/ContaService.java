package com.invest.service;

import com.invest.dto.ContaNewDTO;
import com.invest.dto.ContaUpdateDTO;
import com.invest.entity.Conta;

public interface ContaService {

	public Conta save(ContaNewDTO conta);
	
	public Conta update(ContaUpdateDTO conta);
}
