package com.wefit.test.entity.dto;

import com.wefit.test.entity.enums.Perfil;
import com.wefit.test.entity.enums.TipoPessoa;
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
	private Perfil perfil;
	private String senha;
	private boolean confirme;

}
