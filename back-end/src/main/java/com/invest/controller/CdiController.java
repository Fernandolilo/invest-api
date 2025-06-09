package com.invest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.invest.entity.dto.response.CDIResponseDTO;
import com.invest.service.CdiService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/cdi")
public class CdiController {
	
	private final CdiService service;
	
	
	
	@GetMapping
    public ResponseEntity<CDIResponseDTO> getCDIDiario() {
        return ResponseEntity.ok(service.foundCDI());
    }
	
	

}
