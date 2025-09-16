package com.syp.invest.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
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
import com.syp.invest.entity.dto.response.DolarResponseDTO;
import com.syp.invest.service.DollarService;
import com.syp.invest.service.exceptions.ObjectNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DollarServiceImpl implements DollarService {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    private static final String URL_COMERCIAL_COMPRA =
            "https://api.bcb.gov.br/dados/serie/bcdata.sgs.1/dados/ultimos/1?formato=json";
    private static final String URL_COMERCIAL_VENDA =
            "https://api.bcb.gov.br/dados/serie/bcdata.sgs.10813/dados/ultimos/1?formato=json";

    private static final BigDecimal MARGEM_TURISMO = new BigDecimal("0.06"); // 6%

    @Override
    public DolarResponseDTO foundDollar() {
        try {
            LocalDate data = getData(URL_COMERCIAL_COMPRA);
            BigDecimal valorComercialCompra = getValor(URL_COMERCIAL_COMPRA);
            BigDecimal valorComercialVenda = getValor(URL_COMERCIAL_VENDA);

            if (data != null && valorComercialCompra != null && valorComercialVenda != null) {
                BigDecimal valorTurismoCompra = valorComercialCompra.multiply(BigDecimal.ONE.add(MARGEM_TURISMO))
                        .setScale(4, RoundingMode.HALF_UP);
                BigDecimal valorTurismoVenda = valorComercialVenda.multiply(BigDecimal.ONE.add(MARGEM_TURISMO))
                        .setScale(4, RoundingMode.HALF_UP);

                return DolarResponseDTO.builder()
                        .data(data)
                        .valorComercialCompra(valorComercialCompra)
                        .valorComercialVenda(valorComercialVenda)
                        .valorTurismoCompra(valorTurismoCompra)
                        .valorTurismoVenda(valorTurismoVenda)
                        .build();
            }

            throw new ObjectNotFoundException("Erro ao obter cotações do dólar: valores nulos.");
        } catch (IOException | InterruptedException e) {
            throw new ObjectNotFoundException("Erro ao obter valor do dólar: " + e.getMessage());
        }
    }

    private BigDecimal getValor(String url) throws IOException, InterruptedException {
        String response = sendRequest(url);
        List<Map<String, String>> dataList = objectMapper.readValue(
                response, new TypeReference<List<Map<String, String>>>() {});
        if (dataList != null && !dataList.isEmpty()) {
            return new BigDecimal(dataList.get(0).get("valor")).round(MathContext.DECIMAL64);
        }
        return null;
    }

    private LocalDate getData(String url) throws IOException, InterruptedException {
        String response = sendRequest(url);
        List<Map<String, String>> dataList = objectMapper.readValue(
                response, new TypeReference<List<Map<String, String>>>() {});
        if (dataList != null && !dataList.isEmpty()) {
            return LocalDate.parse(dataList.get(0).get("data"),
                    DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
        return null;
    }

    private String sendRequest(String url) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new ObjectNotFoundException("Erro ao buscar dado do dólar: código " + response.statusCode());
        }

        return response.body();
    }
}
