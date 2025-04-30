package com.wefit.test.entity;

import java.io.Serializable;
import java.util.UUID;

import com.wefit.test.TipoPessoa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Client implements Serializable {
	private static final long serialVersionUID = 1L;

	private UUID id;

	private String nome;
	private String cpfOuCnpj;
	private String celular;
	private String telefone;
	private String email;
	private TipoPessoa tipo;
	private String senha;
	private boolean confirme;

	

}
