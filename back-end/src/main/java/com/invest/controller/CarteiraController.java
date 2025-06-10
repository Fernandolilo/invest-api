package com.invest.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.invest.entity.Investimento;
import com.invest.service.CarteiraService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/carteiras")
public class CarteiraController {
	
	private final CarteiraService service;
	
	@GetMapping("/list")
	@ResponseStatus(HttpStatus.OK)
	public List<Investimento> foundAll(@PathVariable String cpfOuCnpj) {
		return service.investimentos(cpfOuCnpj);
	}
	

}
