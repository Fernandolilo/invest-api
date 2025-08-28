package com.syp.invest.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.syp.invest.entity.CategoriaInvestimento;
import com.syp.invest.entity.dto.CategoriaInvestimentoDTO;
import com.syp.invest.entity.dto.CategoriaInvestimentoNewDTO;
import com.syp.invest.repositories.CategoriaInvestimentosRepository;
import com.syp.invest.service.CategoriaInvestimentosService;
import com.syp.invest.service.exceptions.ObjectNotFoundException;

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

	@Override
	public List<CategoriaInvestimentoDTO> foundListCat() {
		 List<CategoriaInvestimento> categoriaInvestimentos = repository.findAll();

		    if (categoriaInvestimentos.isEmpty()) {
		        throw new ObjectNotFoundException("Nenhuma categoria de investimento encontrada.");
		    }

		    return categoriaInvestimentos.stream()
		            .map(cat -> mapper.map(cat, CategoriaInvestimentoDTO.class))
		            .toList();
		}


}
