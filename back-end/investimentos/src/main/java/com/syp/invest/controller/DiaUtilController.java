package com.syp.invest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syp.invest.entity.dto.response.DiaUtilResponseDTO;
import com.syp.invest.service.DataService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/diaUtil")
public class DiaUtilController {

	private final DataService service;
	
	
	@GetMapping
    public ResponseEntity<DiaUtilResponseDTO> getCDIDiario() {
        return ResponseEntity.ok(service.diaUtil());
    }
	
}
