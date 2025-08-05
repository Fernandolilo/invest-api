package com.invest.serviceTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.invest.dto.ContaNewDTO;
import com.invest.dto.ContaUpdateDTO;
import com.invest.dto.InvestimentoNewDTO;
import com.invest.entity.Client;
import com.invest.entity.Conta;
import com.invest.entity.Investimento;
import com.invest.entity.enums.TipoConta;
import com.invest.entity.enums.TipoPessoa;
import com.invest.reposiotries.ClientRepository;
import com.invest.reposiotries.InvestimentoRepository;
import com.invest.service.ContaService;
import com.invest.service.Impl.InvestimentoServiceImpl;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class InvestimentoServiceTest {

	   @Mock
	    private InvestimentoRepository repository;

	    @Mock
	    private ClientRepository clientRepository;

	    @Mock
	    private ModelMapper mapper;

	    @Mock
	    private ContaService contaService;

	    
	    private InvestimentoServiceImpl investimentoService;


	    private Client client;
	    private Conta conta;
	    private ContaNewDTO contaDTO;
	    private InvestimentoNewDTO investimentoNewDTO;
	    private ContaUpdateDTO contaUpdateDTO;	    
	    
	    @BeforeEach
	    void setUp() {
	    	
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
	    	        		.saldo(BigDecimal.valueOf(100.0))
	    	        		.tipo(TipoConta.CONTA_CORRENTE)
	    	        		.build();
	    	     

	    	        // Conta mapeada
	    	        conta = Conta.builder()
	    	            .id(UUID.randomUUID())
	    	            .agencia(1001)
	    	            .banco(237)
	    	            .numero(123456)
	    	            .saldo(BigDecimal.valueOf(100.0))
	    	            .tipo(TipoConta.CONTA_CORRENTE)
	    	            .client(client)
	    	            .build();
	    	        
	    	        investimentoNewDTO = InvestimentoNewDTO.builder()
	    	        		.valor(BigDecimal.valueOf(100.0))
	    	        		.conta(conta.getId())
	    	        		.cpfOuCnpj(client.getCpfOuCnpj())
	    	        		.build();
	    	        
	    	        
	    	      
	    	        MockitoAnnotations.openMocks(this);

	    	
	    	
	    	
	        investimentoService = new InvestimentoServiceImpl(repository, clientRepository, mapper, contaService);
	    }

	    
	    @Test
	    void save () {
	    	//pega a lista de contas
	    	client.setContas(List.of(conta));
	    	
	    	//verifca o cliente pelo cpf ou cnpj
	    	when(clientRepository.findByCpfOuCnpj(client.getCpfOuCnpj())).thenReturn(Optional.of(client));
	    	
	    	//novo investimento instanciado
	    	Investimento investimentoMapeado = new Investimento(); // instância mock da entidade
	    	
	    	//mapeia um novo investimento
	    	when(mapper.map(any(InvestimentoNewDTO.class), eq(Investimento.class))).thenReturn(investimentoMapeado);
	    	
	    	//objeto para atualizar o saldo da conta
	    	contaUpdateDTO = ContaUpdateDTO
	    		    .builder()
	    		    .saldo(conta.getSaldo().subtract(investimentoNewDTO.getValor()))  // Corrigido aqui
	    		    .cpf(conta.getClient().getCpfOuCnpj())
	    		    .build();
	    	  
	    	  //atualiza a conta passando novo saldo com dados do request
	    	  when(mapper.map(any(Conta.class), eq(ContaUpdateDTO.class))).thenReturn(contaUpdateDTO);
	    	  
	    	  //efetua o investimento
	    	  Investimento result = investimentoService.save(investimentoNewDTO);
	    	  assertNotNull(result);
	    	  verify(repository, times(1)).save(investimentoMapeado);
	          verify(contaService, times(1)).update(contaUpdateDTO);	    	
	    }
}
