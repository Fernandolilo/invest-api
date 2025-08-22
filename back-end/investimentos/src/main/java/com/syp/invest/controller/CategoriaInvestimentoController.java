package com.syp.invest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.syp.invest.entity.dto.CategoriaInvestimentoDTO;
import com.syp.invest.entity.dto.CategoriaInvestimentoNewDTO;
import com.syp.invest.service.CategoriaInvestimentosService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/categorias-investimentos")
@RequiredArgsConstructor
public class CategoriaInvestimentoController {
	
	private final CategoriaInvestimentosService service;
	
	 // Endpoint para salvar uma nova conta
	@PostMapping
   @ResponseStatus(HttpStatus.CREATED)
   public ResponseEntity<CategoriaInvestimentoDTO> insert(@Valid @RequestBody CategoriaInvestimentoNewDTO obj) {
       CategoriaInvestimentoDTO categoria= service.save(obj);
       return ResponseEntity.status(HttpStatus.CREATED).body(categoria);
   }
	


}
