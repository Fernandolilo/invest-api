package com.wefit.test.service;

import java.util.Optional;
import java.util.UUID;

import com.wefit.test.entity.dto.ClientDTO;
import com.wefit.test.entity.dto.ClientNewDTO;
import com.wefit.test.entity.dto.EnderecoNewDTO;
import com.wefit.test.entity.dto.response.ClientResponse;

public interface ClientService {

	ClientDTO save(ClientNewDTO cli, EnderecoNewDTO end);

	Optional<ClientResponse> findById(UUID id);
}
