package com.syp.invest.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.syp.invest.entity.dto.response.IpcaResponseDTO;
import com.syp.invest.service.IpcaService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class IpcaServiceImpl implements IpcaService {

    private static final String URL_MENSAL = "https://api.bcb.gov.br/dados/serie/bcdata.sgs.433/dados/ultimos/1?formato=json";
    private static final String URL_ACUMULADO_ANO = "https://api.bcb.gov.br/dados/serie/bcdata.sgs.13522/dados/ultimos/1?formato=json";
    private static final String URL_ACUMULADO_12_MESES = "https://api.bcb.gov.br/dados/serie/bcdata.sgs.4331/dados/ultimos/1?formato=json";

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public IpcaResponseDTO foundIPCA() {
        try {
            BigDecimal mensal = getValorFromApi(URL_MENSAL);
            BigDecimal acumuladoAno = getValorFromApi(URL_ACUMULADO_ANO);
            BigDecimal acumulado12Meses = getValorFromApi(URL_ACUMULADO_12_MESES);
            LocalDate data = getDataFromApi(URL_MENSAL);

            return IpcaResponseDTO.builder()
                    .data(data)
                    .mensal(mensal)
                    .acumuladoAno(acumuladoAno)
                    .acumulado12Meses(acumulado12Meses)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao consultar o IPCA: " + e.getMessage(), e);
        }
    }

    private BigDecimal getValorFromApi(String url) throws IOException, InterruptedException {
        String response = sendRequest(url);
        List<Map<String, String>> dataList = objectMapper.readValue(
                response, new TypeReference<List<Map<String, String>>>() {});
        if (dataList == null || dataList.isEmpty()) {
            throw new RuntimeException("Não foi possível obter dados da série: " + url);
        }
        return new BigDecimal(dataList.get(0).get("valor"));
    }

    private LocalDate getDataFromApi(String url) throws IOException, InterruptedException {
        String response = sendRequest(url);
        List<Map<String, String>> dataList = objectMapper.readValue(
                response, new TypeReference<List<Map<String, String>>>() {});
        if (dataList == null || dataList.isEmpty()) {
            throw new RuntimeException("Não foi possível obter a data da série: " + url);
        }
        String dataStr = dataList.get(0).get("data"); // formato dd/MM/yyyy
        return LocalDate.parse(dataStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    private String sendRequest(String url) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
