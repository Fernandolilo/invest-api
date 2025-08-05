package com.invest.service;

import com.invest.dto.ClientNewDTO;
import com.invest.entity.Client;

public interface ImageService {

	void processarSelfie(ClientNewDTO cli, Client entity);
}
