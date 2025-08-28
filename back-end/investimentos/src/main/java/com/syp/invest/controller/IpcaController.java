package com.syp.invest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syp.invest.entity.dto.response.IpcaResponseDTO;
import com.syp.invest.service.IpcaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/ipca")
@RequiredArgsConstructor
public class IpcaController {
	
	private final IpcaService service;
	
	@GetMapping(value = "/resumo")
	public IpcaResponseDTO foundIpca() {
		return service.foundIPCA();
	}

}
