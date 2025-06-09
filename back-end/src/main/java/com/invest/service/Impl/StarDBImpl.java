package com.invest.service.Impl;

import java.util.HashSet;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.invest.entity.Client;
import com.invest.entity.Endereco;
import com.invest.entity.enums.Role;
import com.invest.entity.enums.TipoPessoa;
import com.invest.reposiotries.ClientRepository;
import com.invest.reposiotries.EnderecoRepository;
import com.invest.service.StartDB;



@Service
public class StarDBImpl implements StartDB{

	private final ClientRepository clientRepository;
	private final EnderecoRepository enderecoRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	public StarDBImpl(ClientRepository clientRepository, EnderecoRepository enderecoRepository,
			BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.clientRepository = clientRepository;
		this.enderecoRepository = enderecoRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	public void initDB() {
	    // Cria o cliente
	    Client cli = Client.builder()
	            .nome("Fernando")
	            .cpfOuCnpj("12312312311")
	            .celular("11 1234-14567")				
	            .email("fernando@wefit.com.br")
	            .telefone("11 12345678")
	            .tipo(TipoPessoa.PESSOA_FISICA)
	            .role(Role.ADMIN)
	            .confirme(true)
	            .senha(bCryptPasswordEncoder.encode("1234"))
	            .roles(new HashSet<>())
	            .build();
	   cli.addRole(Role.ADMIN);
	    

	    // Cria o endereço
	    Endereco end = Endereco.builder()
	            .cep("12345.123")
	            .logradouro("Rua Faria Lima")
	            .numero("123")
	            .complemento("Predio A")
	            .bairro("Pinheiros")
	            .cidade("São Paulo")
	            .estado("SP")
	            .client(cli) 
	            .build();
	    
	   
	    cli.setEndereco(end);

	    
	    clientRepository.save(cli);
	    enderecoRepository.save(end);
	}


}
