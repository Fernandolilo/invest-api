package com.invest.controller;

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

import com.invest.dto.ClientDTO;
import com.invest.entity.dto.response.ClientResponse;
import com.invest.requests.ClientRequest;
import com.invest.service.ClientService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/clients")
public class ClientController {

	private final ClientService service;
	
	@PostMapping
	public ResponseEntity<ClientDTO> create(@RequestBody @Valid ClientRequest dto) {
	    ClientDTO saved = service.save(dto.getClient(), dto.getEndereco());
	    return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Optional<ClientResponse> findById(@PathVariable UUID id) {
		return service.findById(id);
	}
	
}
