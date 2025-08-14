package com.syp.invest.service;

import com.syp.invest.entity.Investimento;
import com.syp.invest.entity.dto.InvestimentoNewDTO;

public interface InvestimentoService {
	
	public Investimento save(InvestimentoNewDTO obj);

}
