package com.wefit.test.entity.dto;

import com.wefit.test.TipoPessoa;
import com.wefit.test.entity.Endereco;
import com.wefit.test.utils.valid.ClientInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ClientInsert
public class ClientNewDTO {
	
	private String nome;
	private String cpfOuCnpj;
	private String celular;
	private String telefone;
	private String email;
	private TipoPessoa tipo;
	private String senha;
	private boolean confirme;
	

}
