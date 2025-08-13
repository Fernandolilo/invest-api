package com.syp.invest.service;

import java.util.UUID;

import com.syp.invest.entity.request.ContaRequest;

public interface ContaService {
	
	ContaRequest findConta (UUID id);

}
