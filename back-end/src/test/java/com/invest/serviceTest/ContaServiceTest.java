package com.invest.serviceTest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.invest.dto.ContaDTO;
import com.invest.dto.ContaNewDTO;
import com.invest.entity.Client;
import com.invest.entity.Conta;
import com.invest.entity.enums.Role;
import com.invest.entity.enums.TipoConta;
import com.invest.entity.enums.TipoPessoa;
import com.invest.reposiotries.ClientRepository;
import com.invest.reposiotries.ContaRepository;
import com.invest.sercurity.service.UserSecurityDetails;
import com.invest.service.UserService;
import com.invest.service.Impl.ContaServiceImpl;
import com.invest.service.exeptions.ObjectNotFoundException;
import com.invest.service.exeptions.UserAccessNegativeException;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class ContaServiceTest {

	@Mock
	private ContaRepository contaRepository;

	@Mock
	private ClientRepository clientRepository;

	@Mock
	private UserService userService;

	@Mock
	private AuthenticationManager authenticationManager;

	@Mock
	private ModelMapper modelMapper;

	private ContaServiceImpl contaService;

	private Client client;
	private Conta conta;
	private ContaNewDTO contaDTO;

	@BeforeEach
	void setUp() {
		// Cliente fictício
		client = Client.builder().id(UUID.randomUUID()).nome("Fernando").cpfOuCnpj("12345678911")
				.celular("11 99999-9999").email("fernando.nandotania@hotmail.com").telefone("11 1234-5678")
				.senha("senha123").tipo(TipoPessoa.PESSOA_FISICA).confirme(true).build();

		// DTO de entrada
		contaDTO = ContaNewDTO.builder().agencia(1001).agencia(237).numero(123456).nome("Fernando").saldo(BigDecimal.valueOf(100.0))
				.tipo(TipoConta.CONTA_CORRENTE).build();

		// Conta mapeada
		conta = Conta.builder().id(UUID.randomUUID()).agencia(1001).banco(237).numero(123456).saldo(BigDecimal.valueOf(1000.0))
				.tipo(TipoConta.CONTA_CORRENTE).client(client).build();

		MockitoAnnotations.openMocks(this);
		// Inicializa a service passando os mocks para o construtor gerado pelo
		// @RequiredArgsConstructor
		contaService = new ContaServiceImpl(contaRepository, modelMapper, clientRepository, userService);
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

	@Test
	@DisplayName("Deve lançar UserAccessNegativeException quando usuário não tem acesso")
	void deveLancarExcecaoDeAcessoNegado() {
		UUID id = UUID.randomUUID();

		// Criar mock do usuário
		UserSecurityDetails userMock = mock(UserSecurityDetails.class);

		// Simular retorno da autenticação
		when(userService.authenticated()).thenReturn(userMock);

		// Simular que o usuário NÃO tem os papéis esperados
		when(userMock.hasRole(Role.ADMIN)).thenReturn(false);
		when(userMock.hasRole(Role.USER)).thenReturn(false);

		// Executar e verificar a exceção
		assertThatThrownBy(() -> contaService.findById(id)).isInstanceOf(UserAccessNegativeException.class)
				.hasMessageContaining("Acesso negado");
	}

	@Test
	void foundConta_DeveRetornarListaDeContasDTO_QuandoUsuarioTemAcessoTotal() {
	    UUID id = UUID.randomUUID();

	    UserSecurityDetails userMock = mock(UserSecurityDetails.class);
	    when(userService.authenticated()).thenReturn(userMock);
	    when(userMock.getId()).thenReturn(id);

	    // Só use lenient se realmente for necessário manter esse mock
	    lenient().when(userMock.hasRole(Role.ADMIN)).thenReturn(true);
	    lenient().when(userMock.hasRole(Role.USER)).thenReturn(true);

	    Client client = new Client();
	    client.setNome("João da Silva");
	    client.setCpfOuCnpj("12345678900");

	    Conta conta = new Conta();
	    conta.setId(UUID.randomUUID());
	    conta.setClient(client);

	    when(contaRepository.findAllByClientId(id)).thenReturn(List.of(conta));

	    ContaDTO contaDTO = new ContaDTO();
	    contaDTO.setId(conta.getId());
	    contaDTO.setNome(client.getNome());
	    contaDTO.setCpfOuCnpj(client.getCpfOuCnpj());

	    when(modelMapper.map(conta, ContaDTO.class)).thenReturn(contaDTO);

	    List<ContaDTO> resultado = contaService.foundConta();

	    assertNotNull(resultado);
	    assertEquals(1, resultado.size());
	    
	}
	
	@Test
	@DisplayName("Deve retornar uma conta passando um Id de Cleinte")
	void deveRetornarUmaContaPassandoUmIdDeCleinte() {
	    // Arrange
	    UUID contaId = UUID.randomUUID();

	    UserSecurityDetails userMock = mock(UserSecurityDetails.class);
	    when(userService.authenticated()).thenReturn(userMock);
	    when(userMock.hasRole(Role.ADMIN)).thenReturn(true);

	    Client client = new Client();
	    client.setNome("João da Silva");
	    client.setImagem("imagem-em-bytes".getBytes());
	    client.setImagemTipo("image/jpeg");

	    Conta conta = new Conta();
	    conta.setId(contaId);
	    conta.setClient(client);

	    when(contaRepository.findById(contaId)).thenReturn(Optional.of(conta));

	    when(modelMapper.map(any(Conta.class), eq(ContaDTO.class))).thenAnswer(invocation -> new ContaDTO());

	    // Act
	    ContaDTO result = contaService.findById(contaId);

	    // Assert
	    assertNotNull(result);
	    assertEquals("João da Silva", result.getNome());
	    assertTrue(result.getSelfie().startsWith("data:image/jpeg;base64,"));
	}

	@Test
	@DisplayName("Deve lançar UserAccessNegativeException quando usuário não tem acesso")
	void deveLancarExcecaoQuandoUsuarioNaoTemAcesso() {
	    // Arrange
	    UUID contaId = UUID.randomUUID();

	    UserSecurityDetails userMock = mock(UserSecurityDetails.class);
	    when(userService.authenticated()).thenReturn(userMock);

	    // Aqui mocka o usuário SEM acesso (hasFullAccess deve retornar false)
	    when(userMock.hasRole(Role.ADMIN)).thenReturn(false);
	    when(userMock.hasRole(Role.USER)).thenReturn(false);

	    // Act & Assert
	    UserAccessNegativeException exception = assertThrows(
	        UserAccessNegativeException.class,
	        () -> contaService.findById(contaId)
	    );

	    assertEquals("Acesso negado", exception.getMessage());
	}

	
}
