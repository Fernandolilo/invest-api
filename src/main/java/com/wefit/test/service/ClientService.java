package com.wefit.test.service;

import com.wefit.test.entity.dto.ClientDTO;
import com.wefit.test.entity.dto.ClientNewDTO;
import com.wefit.test.entity.dto.EnderecoNewDTO;

public interface ClientService {
	
	ClientDTO save(ClientNewDTO cli, EnderecoNewDTO end);

}
