package com.invest.dto;

import com.invest.entity.enums.Role;
import com.invest.entity.enums.TipoPessoa;
import com.invest.utils.valid.ClientInsert;

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
//	private Role role;
	private String senha;
	private boolean confirme;
	
	private String selfie; // imagem base64 do front


}
