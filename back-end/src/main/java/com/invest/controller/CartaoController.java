package com.invest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.invest.dto.CartaoDTO;
import com.invest.dto.CartaoNewDTO;
import com.invest.service.CartaoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cartoes")
@RequiredArgsConstructor
public class CartaoController {
	
	 private final CartaoService cartaoService;

	    @PostMapping
	    public ResponseEntity<CartaoDTO> criarCartao(@Valid @RequestBody CartaoNewDTO cartaoNewDTO) {
	        CartaoDTO novoCartao = cartaoService.save(cartaoNewDTO);
	        return ResponseEntity.ok(novoCartao);
	    }

}
