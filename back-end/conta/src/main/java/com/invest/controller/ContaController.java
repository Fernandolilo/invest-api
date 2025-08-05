package com.invest.controller;

import java.util.List;
import java.util.Optional;
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

import com.invest.dto.ContaDTO;
import com.invest.dto.ContaNewDTO;
import com.invest.entity.Conta;
import com.invest.entity.dto.response.ClientResponse;
import com.invest.service.ContaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/contas")
@RequiredArgsConstructor
public class ContaController {

	private final ContaService service;

	// Endpoint para salvar uma nova conta
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Conta> criarConta(@Valid @RequestBody ContaNewDTO contaDto) {
		service.save(contaDto);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/cliente")
	public ResponseEntity<List<ContaDTO>> getContasPorCliente() {
		List<ContaDTO> contas = service.foundConta();
		return ResponseEntity.ok().body(contas); // 🔁 importante
	}

	@GetMapping("/{id}")
	public ContaDTO findById(@PathVariable UUID id) {
		return service.findById(id);
	}
}
