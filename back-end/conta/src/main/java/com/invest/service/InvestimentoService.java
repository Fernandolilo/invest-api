package com.invest.service;

import com.invest.dto.InvestimentoNewDTO;
import com.invest.entity.Investimento;

public interface InvestimentoService {
	
	public Investimento save(InvestimentoNewDTO obj);

}
