package com.invest.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.invest.dto.ClientDTO;
import com.invest.dto.ClientNewDTO;
import com.invest.dto.EnderecoNewDTO;
import com.invest.entity.Client;
import com.invest.entity.dto.response.ClientResponse;

public interface ClientService {

	ClientDTO save(ClientNewDTO cli, EnderecoNewDTO end);

	Optional<ClientResponse> findById(UUID id);
	
	Optional<ClientResponse> foundCli(String cpfOuCnpj);
	
	List<Client> findAll();
	


}
