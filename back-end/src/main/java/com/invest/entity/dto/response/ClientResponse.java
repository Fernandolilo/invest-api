package com.invest.entity.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.invest.entity.Carteira;
import com.invest.entity.Conta;
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
    
    private String base64; // string base64 da imagem
	private String nameImage; // nome do arquivo (ex: foto.png)
	private String tipoImagem; // MIME type (ex: image/png)

    private List<Carteira> carteiras = new ArrayList<>();
    private List<Conta> contas = new ArrayList<>();
}

