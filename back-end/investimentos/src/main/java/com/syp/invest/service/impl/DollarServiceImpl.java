package com.syp.invest.service.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.syp.invest.entity.dto.response.DolarResponseDTO;
import com.syp.invest.service.DollarService;
import com.syp.invest.service.exceptions.ObjectNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DollarServiceImpl implements DollarService {

    private static final String URL_COMERCIAL_COMPRA = 
        "https://api.bcb.gov.br/dados/serie/bcdata.sgs.1/dados/ultimos/1?formato=json";
    private static final String URL_COMERCIAL_VENDA  = 
        "https://api.bcb.gov.br/dados/serie/bcdata.sgs.10813/dados/ultimos/1?formato=json";

    private static final BigDecimal MARGEM_TURISMO = new BigDecimal("0.06"); // 6%

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public DolarResponseDTO foundDollar() {
        RestTemplate restTemplate = new RestTemplate();

        try {
            LocalDate data = getData(restTemplate, URL_COMERCIAL_COMPRA);
            BigDecimal valorComercialCompra = getValor(restTemplate, URL_COMERCIAL_COMPRA);
            BigDecimal valorComercialVenda  = getValor(restTemplate, URL_COMERCIAL_VENDA);

            if (data != null && valorComercialCompra != null && valorComercialVenda != null) {
                BigDecimal valorTurismoCompra = valorComercialCompra.multiply(BigDecimal.ONE.add(MARGEM_TURISMO))
                        .setScale(4, RoundingMode.HALF_UP);
                BigDecimal valorTurismoVenda  = valorComercialVenda.multiply(BigDecimal.ONE.add(MARGEM_TURISMO))
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
        } catch (RestClientException e) {
            throw new ObjectNotFoundException("Erro ao obter valor do dólar: " + e.getMessage());
        }
    }

    private BigDecimal getValor(RestTemplate restTemplate, String url) {
        try {
            String response = restTemplate.getForObject(url, String.class); // pega como String
            List<Map<String, String>> dataList = objectMapper.readValue(
                    response, new TypeReference<List<Map<String, String>>>(){});
            if (dataList != null && !dataList.isEmpty()) {
                return new BigDecimal(dataList.get(0).get("valor")).round(MathContext.DECIMAL64);
            }
            return null;
        } catch (Exception e) {
            throw new ObjectNotFoundException("Erro ao obter valor do dólar: " + e.getMessage());
        }
    }

    private LocalDate getData(RestTemplate restTemplate, String url) {
        try {
            String response = restTemplate.getForObject(url, String.class); // pega como String
            List<Map<String, String>> dataList = objectMapper.readValue(
                    response, new TypeReference<List<Map<String, String>>>(){});
            if (dataList != null && !dataList.isEmpty()) {
                return LocalDate.parse(dataList.get(0).get("data"),
                        DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            }
            return null;
        } catch (Exception e) {
            throw new ObjectNotFoundException("Erro ao obter data do dólar: " + e.getMessage());
        }
    }
}
