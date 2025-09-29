package com.syp.invest.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.syp.invest.entity.Investimento;
import com.syp.invest.entity.dto.InvestimentoDTO;
import com.syp.invest.entity.dto.InvestimentoNewDTO;
import com.syp.invest.service.InvestimentoService;

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
    public ResponseEntity<Investimento> insert(@Valid @RequestBody InvestimentoNewDTO obj) {
        Investimento investimento = service.save(obj);
        return ResponseEntity.status(HttpStatus.CREATED).body(investimento);
    }
	
	@GetMapping("/conta/{contaId}")
	public ResponseEntity<List<InvestimentoDTO>> listarPorConta(@PathVariable UUID contaId) {
	    return ResponseEntity.ok(service.findByInvestimentoContaId(contaId));
	}

}
