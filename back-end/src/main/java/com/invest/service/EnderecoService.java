package com.invest.service;

import com.invest.dto.EnderecoNewDTO;
import com.invest.entity.Client;

public interface EnderecoService {

    void salvarEnderecoParaCliente(EnderecoNewDTO enderecoDTO, Client cliente);

}
