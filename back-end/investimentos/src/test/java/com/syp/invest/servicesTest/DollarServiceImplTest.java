package com.syp.invest.servicesTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.syp.invest.entity.dto.response.DolarResponseDTO;
import com.syp.invest.service.exceptions.ObjectNotFoundException;
import com.syp.invest.service.impl.DollarServiceImpl;

@ExtendWith(MockitoExtension.class)
class DollarServiceImplTest {

    @Mock
    private HttpClient httpClient;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private DollarServiceImpl dollarService;

    private static final String URL_COMERCIAL_COMPRA =
        "https://api.bcb.gov.br/dados/serie/bcdata.sgs.1/dados/ultimos/1?formato=json";
    private static final String URL_COMERCIAL_VENDA =
        "https://api.bcb.gov.br/dados/serie/bcdata.sgs.10813/dados/ultimos/1?formato=json";

    @Test
    void foundDollar_shouldReturnDolarResponseDTO_whenApiCallIsSuccessful() throws Exception {
        // 1. Defina as respostas simuladas para cada URL
        String mockResponseCompra = "[{\"data\":\"15/09/2025\",\"valor\":\"5.1500\"}]";
        String mockResponseVenda = "[{\"data\":\"15/09/2025\",\"valor\":\"5.1600\"}]";
        
        // 2. Mock a resposta HTTP para ambas as URLs.
        HttpResponse<String> mockHttpResponseCompra = mock(HttpResponse.class);
        when(mockHttpResponseCompra.statusCode()).thenReturn(200);
        when(mockHttpResponseCompra.body()).thenReturn(mockResponseCompra);
        
        HttpResponse<String> mockHttpResponseVenda = mock(HttpResponse.class);
        when(mockHttpResponseVenda.statusCode()).thenReturn(200);
        when(mockHttpResponseVenda.body()).thenReturn(mockResponseVenda);
        
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockHttpResponseCompra) // Retorna para a primeira chamada (getData)
                .thenReturn(mockHttpResponseCompra) // Retorna para a segunda chamada (getValor de compra)
                .thenReturn(mockHttpResponseVenda);  // Retorna para a terceira chamada (getValor de venda)
        
        // 3. Mock a desserialização do JSON pelo ObjectMapper.
        List<Map<String, String>> dataListCompra = List.of(Map.of("data", "15/09/2025", "valor", "5.1500"));
        List<Map<String, String>> dataListVenda = List.of(Map.of("data", "15/09/2025", "valor", "5.1600"));
        
        when(objectMapper.readValue(anyString(), any(TypeReference.class)))
                .thenReturn(dataListCompra)  // Para getData()
                .thenReturn(dataListCompra)  // Para getValor() de compra
                .thenReturn(dataListVenda);   // Para getValor() de venda
        
        // 4. Chame o método a ser testado.
        DolarResponseDTO result = dollarService.foundDollar();

        // 5. Verifique as asserções.
        assertNotNull(result);
        assertEquals(LocalDate.of(2025, 9, 15), result.getData());
        assertEquals(new BigDecimal("5.1500"), result.getValorComercialCompra());
        assertEquals(new BigDecimal("5.1600"), result.getValorComercialVenda());
    }

    @Test
    void foundDollar_shouldThrowObjectNotFoundException_whenApiReturnsEmptyList() throws Exception {
        // 1. Simule uma resposta HTTP com JSON vazio.
        HttpResponse<String> mockHttpResponse = mock(HttpResponse.class);
        when(mockHttpResponse.statusCode()).thenReturn(200);
        when(mockHttpResponse.body()).thenReturn("[]");

        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockHttpResponse);
        
        // 2. Mock a desserialização do JSON vazio.
        when(objectMapper.readValue(anyString(), any(TypeReference.class)))
                .thenReturn(List.of()); // Retorna uma lista vazia

        // 3. Verifique se a exceção é lançada.
        assertThrows(ObjectNotFoundException.class, () -> dollarService.foundDollar());
    }
    
    @Test
    void foundDollar_shouldThrowObjectNotFoundException_whenRestClientExceptionOccurs() throws Exception {
        // 1. Simule uma exceção de rede.
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(new IOException("Connection refused."));

        // 2. Verifique se a exceção é lançada.
        assertThrows(ObjectNotFoundException.class, () -> dollarService.foundDollar());
    }
}