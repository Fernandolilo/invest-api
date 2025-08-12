package com.syp.invest.service;

import java.util.UUID;

import com.syp.invest.entity.request.Conta;

public interface ContaService {
	
	Conta findConta (UUID id);

}
