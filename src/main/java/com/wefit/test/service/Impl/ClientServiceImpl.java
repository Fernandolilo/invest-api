package com.wefit.test.service.Impl;

import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.wefit.test.entity.Client;
import com.wefit.test.entity.Endereco;
import com.wefit.test.entity.dto.ClientDTO;
import com.wefit.test.entity.dto.ClientNewDTO;
import com.wefit.test.entity.dto.EnderecoNewDTO;
import com.wefit.test.entity.dto.response.ClientResponse;
import com.wefit.test.reposiotries.ClientRepository;
import com.wefit.test.reposiotries.EnderecoRepository;
import com.wefit.test.service.ClientService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ClientServiceImpl implements ClientService {

	private final ModelMapper mapper;
	private final ClientRepository clientRepository;
	private final EnderecoRepository enderecoRepository;

	@Override
	public ClientDTO save(ClientNewDTO cli, EnderecoNewDTO end) {

		Client entity = mapper.map(cli, Client.class);
		Endereco endereco = mapper.map(end, Endereco.class);

		clientRepository.save(entity);

		endereco.setClient(entity);

		enderecoRepository.save(endereco);

		ClientDTO dto = mapper.map(entity, ClientDTO.class);

		return dto;
	}

	@Override
	public Optional<ClientResponse> findById(UUID id) {
	    return clientRepository.findById(id)
	        .map(client -> mapper.map(client, ClientResponse.class));
	}


}
