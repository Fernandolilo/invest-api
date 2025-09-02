package com.syp.invest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syp.invest.entity.dto.response.DolarResponseDTO;
import com.syp.invest.service.DollarService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/dollarXreal")
public class DollarController {

	private final DollarService service;
	
	
	@GetMapping
    public ResponseEntity<DolarResponseDTO> getCDIDiario() {
        return ResponseEntity.ok(service.foundDollar());
    }
}
