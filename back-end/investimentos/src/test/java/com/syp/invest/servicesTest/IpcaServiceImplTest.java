package com.syp.invest.servicesTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.syp.invest.entity.dto.response.IpcaResponseDTO;
import com.syp.invest.service.impl.IpcaServiceImpl;

@ExtendWith(MockitoExtension.class)
class IpcaServiceImplTest {

    @Mock
    private HttpClient httpClient;

    @Mock
    private HttpResponse<String> httpResponse;

    @InjectMocks
    private IpcaServiceImpl ipcaService;

    private static final String URL_MENSAL = "https://api.bcb.gov.br/dados/serie/bcdata.sgs.433/dados/ultimos/1?formato=json";
    private static final String URL_ACUMULADO_ANO = "https://api.bcb.gov.br/dados/serie/bcdata.sgs.13522/dados/ultimos/1?formato=json";
    private static final String URL_ACUMULADO_12_MESES = "https://api.bcb.gov.br/dados/serie/bcdata.sgs.4331/dados/ultimos/1?formato=json";

    @Test
    @DisplayName("Deve retornar dados válidos do IPCA")
    void foundIPCA_success() throws Exception {
        // 1. Mock JSON de resposta
        String mockMensal = "[{\"data\":\"15/09/2025\",\"valor\":\"0.20\"}]";
        String mockAno = "[{\"data\":\"15/09/2025\",\"valor\":\"5.15\"}]";
        String mock12Meses = "[{\"data\":\"15/09/2025\",\"valor\":\"6.20\"}]";

        // 2. Configure comportamento do HttpClient
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(httpResponse);

        // 3. Encadeie as respostas do body
        when(httpResponse.body())
                .thenReturn(mockMensal)
                .thenReturn(mockAno)
                .thenReturn(mock12Meses)
                .thenReturn(mockMensal); // para pegar a data

        // 4. Execute
        IpcaResponseDTO result = ipcaService.foundIPCA();

        // 5. Verifique
        assertNotNull(result);
        assertEquals(LocalDate.of(2025, 9, 15), result.getData());
        assertEquals(new BigDecimal("0.20"), result.getMensal());
        assertEquals(new BigDecimal("5.15"), result.getAcumuladoAno());
        assertEquals(new BigDecimal("6.20"), result.getAcumulado12Meses());
    }

    @Test
    @DisplayName("Deve lançar RuntimeException quando retorno da API for vazio")
    void foundIPCA_emptyResponse() throws Exception {
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(httpResponse);
        when(httpResponse.body()).thenReturn("[]");

        assertThrows(RuntimeException.class, () -> ipcaService.foundIPCA());
    }

    @Test
    @DisplayName("Deve lançar RuntimeException em falha de conexão")
    void foundIPCA_connectionError() throws Exception {
        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(new IOException("Connection refused"));

        assertThrows(RuntimeException.class, () -> ipcaService.foundIPCA());
    }

    @Test
    @DisplayName("Deve lançar RuntimeException em dado inválido")
    void foundIPCA_invalidData() throws Exception {
        String mockInvalid = "[{\"data\":\"15/09/2025\",\"valor\":\"abc\"}]";

        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(httpResponse);
        when(httpResponse.body()).thenReturn(mockInvalid);

        assertThrows(RuntimeException.class, () -> ipcaService.foundIPCA());
    }
}
