package com.invest.entity.dto.response;

import com.invest.entity.enums.Perfil;
import com.invest.entity.enums.TipoPessoa;

import lombok.Data;
@Data
public class ClientResponse {
	//por que o request, por que em DTO e entidade temos dados sensiveis
    private String nome;
    private String cpfOuCnpj;
    private String celular;
    private String telefone;
    private String email;
    private TipoPessoa tipo;
    private Perfil perfil;

    private EnderecoRespponse endereco;
}

