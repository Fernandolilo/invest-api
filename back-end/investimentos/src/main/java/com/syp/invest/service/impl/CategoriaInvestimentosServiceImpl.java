package com.syp.invest.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.syp.invest.entity.CategoriaInvestimento;
import com.syp.invest.entity.dto.CategoriaInvestimentoDTO;
import com.syp.invest.entity.dto.CategoriaInvestimentoNewDTO;
import com.syp.invest.entity.enums.VencimentoInvestimento;
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
		entity.setDataInicio(LocalDate.now());
		  // calcula o vencimento com base no enum informado
		 entity.setDataVencimento(calcularDataVencimento(entity.getCarencia(), obj.getDataVencimento()));


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
	
	private LocalDate calcularDataVencimento(VencimentoInvestimento carencia, LocalDate dataManual) {
	    LocalDate hoje = LocalDate.now();

	    switch (carencia) {
	        case QUALQUER_INSTANTE:
	            return hoje;
	        case DIAS_30:
	            return hoje.plusDays(30);
	        case DIAS_60:
	            return hoje.plusDays(60);
	        case DIAS_90:
	            return hoje.plusDays(90);
	        case DIAS_180:
	            return hoje.plusDays(180);
	        case ANUAL:
	            return hoje.plusYears(1);
	        case VENCIMENTO:
	            if (dataManual == null) {
	                throw new IllegalArgumentException("A data de vencimento manual deve ser informada quando a carência for VENCIMENTO.");
	            }
	            return dataManual;
	        default:
	            throw new IllegalArgumentException("Carência não suportada: " + carencia);
	    }
	}



}
