package com.invest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.invest.dto.InvestimentoNewDTO;
import com.invest.entity.Conta;
import com.invest.service.InvestimentoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/investimentos")
@RequiredArgsConstructor
public class InvestimentoController {
	
	private final InvestimentoService service;
	
	 // Endpoint para salvar uma nova conta
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Conta> insert (@Valid @RequestBody InvestimentoNewDTO obj) {
        service.save(obj);
        return ResponseEntity.noContent().build();
    }
    


}
