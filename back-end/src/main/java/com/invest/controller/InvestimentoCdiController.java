package com.invest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.invest.service.CdiService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/investimentos")
@RequiredArgsConstructor
public class InvestimentoCdiController {
	
	private final CdiService investimentoCdi102;

    @PostMapping("/aplicar")
    public ResponseEntity<String> aplicarRendimentoDiario() {
        investimentoCdi102.foundCDI();
        return ResponseEntity.ok("Rendimento diário aplicado com sucesso.");
    }

}
