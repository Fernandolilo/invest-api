package com.wefit.test;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.wefit.test.entity.Client;
import com.wefit.test.entity.Endereco;
import com.wefit.test.entity.dto.ClientNewDTO;
import com.wefit.test.entity.dto.EnderecoNewDTO;
import com.wefit.test.service.ClientService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@SpringBootApplication
public class TestTechWefitApplication implements CommandLineRunner {

	private final ClientService service;


	public static void main(String[] args) {
		SpringApplication.run(TestTechWefitApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
		 ClientNewDTO cli = ClientNewDTO.builder()
		            .nome("Fernando")
		            .cpfOuCnpj("12345678912")
		            .celular("11 1234-14567")				
		            .email("fernando@wefit.com.br")
		            .telefone("11 12345678")
		            .tipo(TipoPessoa.PESSOA_FISICA)
		            .confirme(true)
		            .senha("1234")
		            .build();

		    // Cria o endereço
		    EnderecoNewDTO end = EnderecoNewDTO.builder()
		            .cep("12345.123")
		            .logradouro("Rua Faria Lima")
		            .numero("123")
		            .complemento("Predio A")
		            .bairro("Pinheiros")
		            .cidade("São Paulo")
		            .estado("SP")
		           
		            .build();	    
		   

		    service.save(cli, end);
		
	}

}
