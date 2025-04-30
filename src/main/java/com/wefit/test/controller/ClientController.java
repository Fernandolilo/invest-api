package com.wefit.test.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.wefit.test.entity.dto.ClientDTO;
import com.wefit.test.entity.dto.response.ClientNewDtoResponse;
import com.wefit.test.service.ClientService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/clients")
public class ClientController {

	private final ClientService service;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ClientDTO create(@RequestBody @Valid ClientNewDtoResponse dto) {
		return service.save(dto.getClient(), dto.getEndereco());
	}

}
