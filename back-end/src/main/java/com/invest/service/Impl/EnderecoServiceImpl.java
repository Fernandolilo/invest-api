package com.invest.service.Impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.invest.dto.EnderecoNewDTO;
import com.invest.entity.Client;
import com.invest.entity.Endereco;
import com.invest.reposiotries.EnderecoRepository;
import com.invest.service.EnderecoService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class EnderecoServiceImpl implements EnderecoService {
    
	private final EnderecoRepository enderecoRepository;
    private final ModelMapper mapper;

 
    @Override
    public void salvarEnderecoParaCliente(EnderecoNewDTO enderecoDTO, Client cliente) {
        Endereco endereco = mapper.map(enderecoDTO, Endereco.class);
        endereco.setClient(cliente);
        enderecoRepository.save(endereco);
    }
}
