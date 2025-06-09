package com.invest.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.invest.dto.CDIValorDTO;
import com.invest.entity.dto.response.CDIResponseDTO;
import com.invest.service.CdiService;
import com.invest.service.exeptions.ObjectNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CDIImpl implements CdiService {

	private static final String URL_CDI = "https://api.bcb.gov.br/dados/serie/bcdata.sgs.12/dados/ultimos/1?formato=json";

	@Override
	public CDIResponseDTO foundCDI() {
		RestTemplate restTemplate = new RestTemplate();
		CDIValorDTO[] resposta = restTemplate.getForObject(URL_CDI, CDIValorDTO[].class);

		if (resposta != null && resposta.length > 0) {
			String data = resposta[0].getData();
			double valor = Double.parseDouble(resposta[0].getValor());
			double anual = calcularCDIAnual(valor);
			return new CDIResponseDTO(data, valor, anual);
		}

		throw new ObjectNotFoundException("Erro ao obter o CDI diário.");
	}

	private double calcularCDIAnual(double cdiDiarioPercentual) {
		double diario = cdiDiarioPercentual / 100;
		return (Math.pow(1 + diario, 252) - 1) * 100;
	}

}
