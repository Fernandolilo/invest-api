package com.syp.invest.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.syp.invest.entity.CategoriaInvestimento;
import com.syp.invest.entity.dto.CategoriaInvestimentoDTO;
import com.syp.invest.entity.dto.CategoriaInvestimentoNewDTO;
import com.syp.invest.repositories.CategoriaInvestimentosRepository;
import com.syp.invest.service.CategoriaInvestimentosService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CategoriaInvestimentosServiceImpl implements CategoriaInvestimentosService {

	private final CategoriaInvestimentosRepository repository;
	private final ModelMapper mapper;

	@Override
	public CategoriaInvestimentoDTO save(CategoriaInvestimentoNewDTO obj) {
		CategoriaInvestimento entity = mapper.map(obj, CategoriaInvestimento.class);
		repository.save(entity);
		return mapper.map(entity, CategoriaInvestimentoDTO.class);
	}

}
