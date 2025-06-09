package com.invest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.invest.dto.ContaNewDTO;
import com.invest.entity.Conta;
import com.invest.service.ContaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/contas")
@RequiredArgsConstructor
public class ContaController {
	
	private final ContaService contaService;

    // Endpoint para salvar uma nova conta
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Conta> criarConta(@Valid @RequestBody ContaNewDTO contaDto) {
        contaService.save(contaDto);
        return ResponseEntity.noContent().build();
    }
    

}
