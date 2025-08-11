package com.syp.client.response;

import com.syp.client.enums.TipoPessoa;

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

   
}
