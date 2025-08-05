package com.invest.service;

import com.invest.entity.enums.BandeiraCartao;

public interface GenereteCartaoService {

        /**
     * Gera apenas o número do cartão, válido de acordo com a bandeira
     * 
     * @param bandeira Bandeira do cartão
     * @return Número do cartão
     */
    String gerarNumeroCartao(BandeiraCartao bandeira);

        
}
