package com.syp.invest.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.syp.invest.entity.dto.response.IpcaResponseDTO;
import com.syp.invest.service.IpcaService;

@Service
public class IpcaServiceImpl implements IpcaService {

	// https://api.bcb.gov.br/dados/serie/bcdata.sgs.433/dados/ultimos/1?formato=json
	private static final String URL_MENSAL = "https://api.bcb.gov.br/dados/serie/bcdata.sgs.433/dados/ultimos/1?formato=json";
	private static final String URL_ACUMULADO_ANO = "https://api.bcb.gov.br/dados/serie/bcdata.sgs.13522/dados/ultimos/1?formato=json";
	private static final String URL_ACUMULADO_12_MESES = "https://api.bcb.gov.br/dados/serie/bcdata.sgs.4331/dados/ultimos/1?formato=json";

	private final RestTemplate restTemplate = new RestTemplate();

	@Override
	public IpcaResponseDTO foundIPCA() {
		BigDecimal mensal = getValorFromApi(URL_MENSAL);
		BigDecimal acumuladoAno = getValorFromApi(URL_ACUMULADO_ANO);
		BigDecimal acumulado12Meses = getValorFromApi(URL_ACUMULADO_12_MESES);
		LocalDate data = getDataFromApi(URL_MENSAL);

		return IpcaResponseDTO.builder().data(data).mensal(mensal).acumuladoAno(acumuladoAno)
				.acumulado12Meses(acumulado12Meses).build();
	}

	private BigDecimal getValorFromApi(String url) {
		List<Map<String, String>> response = restTemplate.getForObject(url, List.class);
		if (response == null || response.isEmpty()) {
			throw new RuntimeException("Não foi possível obter dados da série: " + url);
		}
		String valor = response.get(0).get("valor");
		return new BigDecimal(valor.replace(",", "."));
	}

	private LocalDate getDataFromApi(String url) {
		List<Map<String, String>> response = restTemplate.getForObject(url, List.class);
		if (response == null || response.isEmpty()) {
			throw new RuntimeException("Não foi possível obter a data da série: " + url);
		}
		String dataStr = response.get(0).get("data"); // formato dd/MM/yyyy
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return LocalDate.parse(dataStr, formatter);
	}
}
