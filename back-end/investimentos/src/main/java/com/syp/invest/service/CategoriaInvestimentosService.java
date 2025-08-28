package com.syp.invest.service;

import java.util.List;

import com.syp.invest.entity.dto.CategoriaInvestimentoDTO;
import com.syp.invest.entity.dto.CategoriaInvestimentoNewDTO;

public interface CategoriaInvestimentosService {
	
	CategoriaInvestimentoDTO save (CategoriaInvestimentoNewDTO obj);
	
	public List<CategoriaInvestimentoDTO> foundListCat();

}
