package com.wefit.test.entity.dto;

import java.util.UUID;

import com.wefit.test.TipoPessoa;
import com.wefit.test.entity.Client;
import com.wefit.test.entity.Endereco;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {
	
	private String nome;
	private String cpfOuCnpj;
	private String celular;
	private String telefone;
	private String email;
	private TipoPessoa tipo;
	private String senha;
	private boolean confirme;
	private Endereco endereco;
}
