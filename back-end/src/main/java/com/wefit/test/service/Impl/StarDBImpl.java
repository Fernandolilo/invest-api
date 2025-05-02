package com.wefit.test.service.Impl;

import org.springframework.stereotype.Service;

import com.wefit.test.entity.Client;
import com.wefit.test.entity.Endereco;
import com.wefit.test.entity.enums.Perfil;
import com.wefit.test.entity.enums.TipoPessoa;
import com.wefit.test.reposiotries.ClientRepository;
import com.wefit.test.reposiotries.EnderecoRepository;
import com.wefit.test.service.StartDB;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class StarDBImpl implements StartDB{

	private final ClientRepository clientRepository;
	private final EnderecoRepository enderecoRepository;
	
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
	            .perfil(Perfil.COMPRADOR)
	            .confirme(true)
	            .senha("1234")
	            .build();

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
