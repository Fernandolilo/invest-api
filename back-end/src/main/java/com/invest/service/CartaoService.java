package com.invest.service;

import com.invest.dto.CartaoDTO;
import com.invest.dto.CartaoNewDTO;

public interface CartaoService {

	public CartaoDTO save(CartaoNewDTO cartao);
}
