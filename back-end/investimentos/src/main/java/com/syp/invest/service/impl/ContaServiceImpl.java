package com.syp.invest.service.impl;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.syp.invest.entity.request.ContaRequest;
import com.syp.invest.service.ContaService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ContaServiceImpl implements ContaService {

    private static final String BASE_URL = "http://localhost:8765/api/invest/contas/";

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public ContaServiceImpl() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public ContaRequest findConta(UUID id) {
        try {
            // Monta a URL
            String url = BASE_URL + id;

            // Cria requisição GET
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            // Executa requisição
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Verifica status HTTP
            if (response.statusCode() != 200) {
                throw new RuntimeException("Erro ao buscar conta. Status: " + response.statusCode());
            }

            // Converte JSON para objeto ContaRequest
            return objectMapper.readValue(response.body(), ContaRequest.class);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Erro ao executar requisição HTTP", e);
        }
    }
}