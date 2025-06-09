package com.invest.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.invest.dto.ContaNewDTO;
import com.invest.entity.Client;
import com.invest.entity.Conta;
import com.invest.entity.enums.TipoConta;
import com.invest.entity.enums.TipoPessoa;
import com.invest.reposiotries.ClientRepository;
import com.invest.reposiotries.ContaRepository;
import com.invest.service.Impl.ContaServiceImpl;
import com.invest.service.exeptions.ObjectNotFoundException;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class ContaServiceTest {

    @Mock
    private ContaRepository contaRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ModelMapper modelMapper;

    private ContaServiceImpl contaService;

    private Client client;
    private Conta conta;
    private ContaNewDTO contaDTO;


    @BeforeEach
    void setUp() {
    	// Cliente fictício
        client = Client.builder()
            .id(UUID.randomUUID())
            .nome("Fernando")
            .cpfOuCnpj("12345678911")
            .celular("11 99999-9999")
            .email("fernando.nandotania@hotmail.com")
            .telefone("11 1234-5678")
            .senha("senha123")
            .tipo(TipoPessoa.PESSOA_FISICA)
            .confirme(true)
            .build();

        // DTO de entrada
        contaDTO = ContaNewDTO.builder().
        		agencia(1001)
        		.agencia(237)
        		.numero(123456)
        		.saldo(100.0)
        		.tipo(TipoConta.CONTA_CORRENTE)
        		.build();
     

        // Conta mapeada
        conta = Conta.builder()
            .id(UUID.randomUUID())
            .agencia(1001)
            .banco(237)
            .numero(123456)
            .saldo(1000.0)
            .tipo(TipoConta.CONTA_CORRENTE)
            .client(client)
            .build();

        MockitoAnnotations.openMocks(this);
        // Inicializa a service passando os mocks para o construtor gerado pelo @RequiredArgsConstructor
        contaService = new ContaServiceImpl(contaRepository, modelMapper, clientRepository);
    }

  
    @Test
    @DisplayName("Deve salvar conta quando cliente existirs")
    void deveSalvarContaComClienteExistentes() {
        // Arrange
        when(clientRepository.findByCpfOuCnpj(contaDTO.getCpf())).thenReturn(Optional.of(client));
        when(modelMapper.map(contaDTO, Conta.class)).thenReturn(conta);
        conta.setClient(client);
        
        when(contaRepository.save(any(Conta.class))).thenReturn(conta);

        // Act
        Conta result = contaService.save(contaDTO); // <-- use o método real da service

        // Assert
        assertNotNull(result);
        assertEquals(conta.getNumero(), result.getNumero());
        assertEquals(client.getId(), result.getClient().getId());
        verify(contaRepository, times(1)).save(conta);
    }
    
    @Test
    @DisplayName("Deve lançar exceção quando cliente não existir")
    void deveLancarExcecaoQuandoClienteNaoExistir() {
        // Arrange
        when(clientRepository.findByCpfOuCnpj(contaDTO.getCpf())).thenReturn(Optional.empty());

        // Act & Assert
        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> {
            contaService.save(contaDTO);
        });

        assertTrue(exception.getMessage().contains("Cliente não encontrado"));
    }

    

}



