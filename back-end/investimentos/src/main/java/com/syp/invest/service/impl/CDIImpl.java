	package com.syp.invest.service.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.syp.invest.entity.dto.CDIValorDTO;
import com.syp.invest.entity.dto.response.CDIResponseDTO;
import com.syp.invest.service.CdiService;
import com.syp.invest.service.exceptions.ObjectNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CDIImpl implements CdiService {

    private static final String URL_CDI = 
        "https://api.bcb.gov.br/dados/serie/bcdata.sgs.12/dados/ultimos/1?formato=json";

    private final RestTemplate restTemplate;
    
    @Override
    public CDIResponseDTO foundCDI() {
        CDIValorDTO[] resposta = restTemplate.getForObject(URL_CDI, CDIValorDTO[].class);

        if (resposta != null && resposta.length > 0) {
            String data = resposta[0].getData();

            // Converte a String diretamente para BigDecimal sem passar por double
            BigDecimal valorPercentual = new BigDecimal(resposta[0].getValor(), MathContext.DECIMAL64);

            // Calcula CDI anual
            BigDecimal anual = calcularCDIAnual(valorPercentual);

            return new CDIResponseDTO(data, valorPercentual, anual);
        }

        throw new ObjectNotFoundException("Erro ao obter o CDI diário.");
    }

    private BigDecimal calcularCDIAnual(BigDecimal cdiDiarioPercentual) {
        // Converte de percentual para decimal
        BigDecimal diario = cdiDiarioPercentual.divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);

        // (1 + diario) ^ 252 - 1
        BigDecimal base = BigDecimal.ONE.add(diario);
        double pot = Math.pow(base.doubleValue(), 252);

        BigDecimal resultado = BigDecimal.valueOf(pot).subtract(BigDecimal.ONE)
                .multiply(BigDecimal.valueOf(100)); // volta para percentual

        return resultado.setScale(6, RoundingMode.HALF_UP);
    }
}


