package com.invest.service;

import com.invest.dto.ClientDTO;
import com.invest.dto.ContaNewDTO;

public interface ContaFactoryService {

	ContaNewDTO criarContaParaCliente(ClientDTO clientDTO);

}
