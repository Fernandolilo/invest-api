package com.invest.service;

import java.util.List;

import com.invest.entity.Investimento;

public interface CarteiraService {
	
	public List<Investimento> investimentos(String cpfOuCnpj);
		
}
