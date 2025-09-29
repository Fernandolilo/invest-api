package com.syp.invest.servicesTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import com.syp.invest.entity.dto.CDIValorDTO;
import com.syp.invest.entity.dto.response.CDIResponseDTO;
import com.syp.invest.service.exceptions.ObjectNotFoundException;
import com.syp.invest.service.impl.CDIImpl;

@ExtendWith(MockitoExtension.class)
class CDIImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CDIImpl cdiImpl;

    @Test
    @DisplayName("Deve testa os metodos de retorno do CDI")
    void foundCDI() {
        // 1. Crie a resposta simulada para o RestTemplate.getForObject().
        //    Use um array com um objeto CDIValorDTO com valores válidos.
        CDIValorDTO[] mockResponse = new CDIValorDTO[]{
            new CDIValorDTO("15/09/2025", "0.5541")
        };
        
        // 2. Configure o comportamento do mock do RestTemplate.
        //    Ele deve retornar o 'mockResponse' quando a chamada for feita.
        when(restTemplate.getForObject(anyString(), any(Class.class)))
                .thenReturn(mockResponse);

        // 3. Chame o método que está sendo testado.
        CDIResponseDTO result = cdiImpl.foundCDI();

        // 4. Verifique se o resultado é o esperado.
        assertNotNull(result);
        assertEquals("15/09/2025", result.getData());
        assertEquals(new BigDecimal("0.5541"), result.getCdiDiario());
        assertNotNull(result.getCdiAnual());
    }

    
    @Test
    @DisplayName("Deve testar acesso a integração com api do BCB para retorno do CDI")
    void foundCDIInBcb() {
        // 1. Defina a URL usada na implementação
        final String URL_CDI =
            "https://api.bcb.gov.br/dados/serie/bcdata.sgs.12/dados/ultimos/1?formato=json";

        // 2. Crie a resposta simulada para o RestTemplate.getForObject().
        CDIValorDTO[] mockResponse = new CDIValorDTO[] {
            new CDIValorDTO("15/09/2025", "0.5541")
        };

        // 3. Configure o comportamento do mock do RestTemplate.
        when(restTemplate.getForObject(URL_CDI, CDIValorDTO[].class))
                .thenReturn(mockResponse);

        // 4. Chame o método que está sendo testado.
        CDIResponseDTO result = cdiImpl.foundCDI();

        // 5. Verifique se o resultado é o esperado.
        assertNotNull(result);
        assertEquals("15/09/2025", result.getData());
        assertEquals(new BigDecimal("0.5541"), result.getCdiDiario());
        assertNotNull(result.getCdiAnual());
    }
    // Você ainda pode ter um teste de falha para o caso de resposta vazia
   
    @Test
    @DisplayName("Deve testa caso haja falha na integração com Banco central ")
    void notFoundCDI() {
        // Simule a API retornando um array vazio
        when(restTemplate.getForObject(anyString(), any(Class.class)))
                .thenReturn(new CDIValorDTO[]{});
        
        // Verifique se a exceção é lançada
        assertThrows(ObjectNotFoundException.class, () -> cdiImpl.foundCDI());
    }
    
    @Test
    @DisplayName("Deve lançar error quando houver falha na integração com banco central para busca do CDI")
    void foundCDI_connectionRefused() {
        // 1. Defina a URL usada na implementação
        final String URL_CDI =
            "https://api.bcb.gov.br/dados/serie/bcdata.sgs.12/dados/ultimos/1?formato=json";

        // 2. Simule a falha no RestTemplate
        when(restTemplate.getForObject(URL_CDI, CDIValorDTO[].class))
                .thenThrow(new ObjectNotFoundException("Connection refused"));

        // 3. Verifique se a exceção esperada é lançada
        assertThrows(ObjectNotFoundException.class, () -> cdiImpl.foundCDI());
    }

}