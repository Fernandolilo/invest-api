package com.wefit.test.entity.dto.response;

import java.io.Serializable;

import com.wefit.test.entity.enums.Perfil;
import com.wefit.test.entity.enums.TipoPessoa;

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

