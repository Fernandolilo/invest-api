package com.wefit.test.entity.dto;

import com.wefit.test.entity.Endereco;
import com.wefit.test.entity.enums.Perfil;
import com.wefit.test.entity.enums.TipoPessoa;

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
	private Perfil perfil;
	private String senha;
	private boolean confirme;
	private Endereco endereco;
}
