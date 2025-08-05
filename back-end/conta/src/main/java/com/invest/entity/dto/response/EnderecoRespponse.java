package com.invest.entity.dto.response;

import lombok.Data;

@Data
public class EnderecoRespponse {
    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
}
