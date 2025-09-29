package com.syp.invest.service;

import java.util.List;
import java.util.UUID;

import com.syp.invest.entity.Investimento;
import com.syp.invest.entity.dto.InvestimentoDTO;
import com.syp.invest.entity.dto.InvestimentoNewDTO;

public interface InvestimentoService {
	
	public Investimento save(InvestimentoNewDTO obj);
	
	public List<InvestimentoDTO> findByInvestimentoContaId(UUID id);

}
