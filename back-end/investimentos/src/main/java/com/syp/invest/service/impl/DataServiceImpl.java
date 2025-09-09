package com.syp.invest.service.impl;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.syp.invest.entity.dto.response.DiaUtilResponseDTO;
import com.syp.invest.entity.dto.response.FeriadoResponse;
import com.syp.invest.service.DataService;
import com.syp.invest.service.exceptions.ObjectNotFoundException;

@Service
public class DataServiceImpl implements DataService {

    private static final LocalDate agora = LocalDate.now();
    private static final String FERIADO_NACIONAL = "https://feriadosbancarios.febraban.org.br/Home/ObterFeriadosFederais?ano=" + agora.getYear();

    private static final HttpClient client = HttpClient.newHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public DiaUtilResponseDTO diaUtil() {
        LocalDate hoje = LocalDate.now();

        // Busca se é feriado ou não
        Optional<FeriadoResponse> feriado = buscarFeriado(hoje);

        boolean util = isDiaUtil(hoje, feriado);

        // Monta DTO
        DiaUtilResponseDTO dto = new DiaUtilResponseDTO();
        dto.setDia(hoje);
        dto.setDiaUtil(util);
        dto.setName(feriado.map(FeriadoResponse::getNomeFeriado).orElse(util ? "Dia útil" : "Fim de semana"));
       

        return dto;
    }

  

    private Optional<FeriadoResponse> buscarFeriado(LocalDate data) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(FERIADO_NACIONAL))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new ObjectNotFoundException("Erro ao buscar feriados: " + response.statusCode());
            }

            List<FeriadoResponse> feriados = mapper.readValue(
                    response.body(), new TypeReference<List<FeriadoResponse>>() {}
            );

            return feriados.stream()
                    .filter(f -> parseDiaMes(f.getDiaMes()).equals(data))
                    .findFirst();

        } catch (IOException | InterruptedException e) {
            throw new ObjectNotFoundException("Erro na comunicação com Febraban");
        }
    }

    private LocalDate parseDiaMes(String diaMes) {
        // Ex: "01 de janeiro"
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern("dd 'de' MMMM")
                .parseDefaulting(ChronoField.YEAR, LocalDate.now().getYear())
                .toFormatter(new Locale("pt", "BR"));

        return LocalDate.parse(diaMes, formatter);
    }
    
    private boolean isDiaUtil(LocalDate data, Optional<FeriadoResponse> feriado) {
        DayOfWeek dia = data.getDayOfWeek();
        if (dia == DayOfWeek.SATURDAY || dia == DayOfWeek.SUNDAY) {
            return false;
        }
        return feriado.isEmpty();
    }
}
