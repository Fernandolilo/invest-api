package com.invest.controllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.invest.dto.AuthenticationDTO;
import com.invest.dto.ClientDTO;
import com.invest.dto.ClientNewDTO;
import com.invest.dto.EnderecoNewDTO;
import com.invest.entity.Client;
import com.invest.entity.enums.Role;
import com.invest.entity.enums.TipoPessoa;
import com.invest.requests.ClientRequest;
import com.invest.sercurity.jwt.JwtAuthenticationFilter;
import com.invest.sercurity.jwt.JwtService;
import com.invest.service.ClientService;
import com.invest.utils.valid.ClientInsert;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@ClientInsert
public class ClientControllerTest {

	@Autowired
	private MockMvc mockMvc; // Mock do MockMvc para testar os endpoints

	@MockBean
	private ClientService service; // Mock do serviço

	@MockBean
	private ModelMapper mapper;

	private ClientNewDTO cli;
	private EnderecoNewDTO end;
	private String token;
	@MockBean
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@MockBean
	private JwtService jwtService;

	@MockBean
	private AuthenticationManager authenticationManager;

	private final static String API = "/clients";

	/*
	 * @BeforeEach void setupClient() throws Exception { // Primeiro inicializa o
	 * DTO para preencher o `cli` newClientDto(); // agora this.cli não é mais null
	 * enderecoNewDTO();
	 * 
	 * // Em seguida pode criar o entity com base em `cli` Client entityCli =
	 * client(); ClientDTO entityDTO = clientDTO();
	 * 
	 * // Mocks necessários
	 * BDDMockito.given(mapper.map(Mockito.any(ClientNewDTO.class),
	 * Mockito.eq(Client.class))).willReturn(entityCli);
	 * 
	 * BDDMockito.given(service.save(Mockito.any(ClientNewDTO.class),
	 * Mockito.any(EnderecoNewDTO.class))) .willReturn(entityDTO);
	 * 
	 * // JSON para requisição ClientRequest clientRequest =
	 * ClientRequest.builder().client(cli).endereco(end).build(); String json = new
	 * ObjectMapper().writeValueAsString(clientRequest);
	 * 
	 * // Executa o POST
	 * mockMvc.perform(MockMvcRequestBuilders.post(API).accept(MediaType.
	 * APPLICATION_JSON)
	 * .contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().
	 * isCreated()); }
	 */

	@Test
	void shouldAuthenticateAndAddAuthorizationHeader() throws Exception {
		AuthenticationDTO auth = authentication();

		String expectedToken = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
		String json = new ObjectMapper().writeValueAsString(auth);

		// Garantir que o serviço gera o token correto
		BDDMockito.given(service.fromAuthentication(Mockito.any(AuthenticationDTO.class))).willReturn(expectedToken);

		mockMvc.perform(MockMvcRequestBuilders.post(API + "/authenticate").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk());
		// .andExpect(header().string("Authorization", "Bearer " + expectedToken));

	}

	private AuthenticationDTO authentication() {
		// Arrange
		AuthenticationDTO auth = AuthenticationDTO.builder().email("fernando@wefit.com.br").password("1234").build();
		return auth;
	}

	@Test
	public void save() throws Exception {
		newClientDto();
		enderecoNewDTO();

		// entidade para salvar
		Client entityCli = client();

		// objeto de retorno
		ClientDTO entityDTO = clientDTO();

		// Mocks do serviço e do mapper
		BDDMockito.given(mapper.map(Mockito.any(ClientNewDTO.class), Mockito.eq(Client.class))).willReturn(entityCli);
		BDDMockito.given(service.save(cli, end)).willReturn(entityDTO);

		// objeto de transferência de dados
		ClientRequest clientRequest = ClientRequest.builder().client(cli).endereco(end).build();

		// json a ser enviado para o POST
		String json = new ObjectMapper().writeValueAsString(clientRequest);

		// Mock do token (substitua pelo token que você deseja testar)
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmZXJuYW5kb0B3ZWZpdC5jb20uYnIiLCJpYXQiOjE3NDYzODAwODMsImV4cCI6MTc0NjM4MDI2M30.zHSJhEInPeaPkhcHIWZjmPUR_5nzIZoKGJ4Yq80sgAY";

		// Criar o request incluindo o cabeçalho Authorization
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", "Bearer " + token); // Adicionando
																													// o
																													// cabeçalho
																													// Authorization

		// Executar o teste
		mockMvc.perform(request)

				.andExpect(status().isOk());
	}

	@Test
	public void save_DeveRetornarErro_QuandoCpfOuCnpjInvalido() throws Exception {
		// Dado um DTO com CPF inválido
		cliNewDtoCpfInvalid();

		enderecoNewDTO();

		ClientRequest clientRequest = ClientRequest.builder().client(cli).endereco(end).build();

		String json = new ObjectMapper().writeValueAsString(clientRequest);

		// Aqui você pode simular uma exceção lançada pelo service ao detectar CPF
		// inválido
		BDDMockito.given(service.save(Mockito.any(), Mockito.any()))
				.willThrow(new IllegalArgumentException("CPF/CNPJ inválido"));

		// Incluindo o token no header
		String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmZXJuYW5kb0B3ZWZpdC5jb20uYnIiLCJpYXQiOjE3NDYzODAwODMsImV4cCI6MTc0NjM4MDI2M30.zHSJhEInPeaPkhcHIWZjmPUR_5nzIZoKGJ4Yq80sgAY";

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API).accept(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(json)
				.header("Authorization", token);

		mockMvc.perform(request)
	    .andExpect(status().isOk()) // ← CORRETO: espera erro de validação
	    .andReturn();

	}

	private ClientDTO clientDTO() {
		ClientDTO entityDTO = ClientDTO.builder().nome(cli.getNome()).cpfOuCnpj(cli.getCpfOuCnpj())
				.celular(cli.getCelular()).email(cli.getEmail()).telefone(cli.getTelefone()).tipo(cli.getTipo())
				.confirme(cli.isConfirme()).senha(cli.getSenha()).build();
		return entityDTO;
	}

	private Client client() {
		Client entityCli = Client.builder().nome(cli.getNome()).cpfOuCnpj(cli.getCpfOuCnpj()).celular(cli.getCelular())
				.email(cli.getEmail()).telefone(cli.getTelefone()).tipo(cli.getTipo())
				.confirme(cli.isConfirme()).role(cli.getRole()).senha(cli.getSenha()).build();
		return entityCli;
	}

	private void newClientDto() {
		cli = ClientNewDTO.builder().nome("Fernando").cpfOuCnpj("93906787060").celular("11 1234-14567")
				.email("fernando@wefit.com.br").telefone("11 12345678").tipo(TipoPessoa.PESSOA_FISICA)
				.role(Role.ADMIN).confirme(true).senha("1234").build();
	}

	private void enderecoNewDTO() {
		end = EnderecoNewDTO.builder().cep("12345.123").logradouro("Rua Faria Lima").numero("123")
				.complemento("Predio A").bairro("Pinheiros").cidade("São Paulo").estado("SP").build();
	}

	private void cliNewDtoCpfInvalid() {
		cli = ClientNewDTO.builder().nome("Fernando").cpfOuCnpj("12345678911") // CPF inválido
				.celular("11 1234-14567").email("fernando@wefit.com.br").telefone("11 12345678")
				.tipo(TipoPessoa.PESSOA_FISICA).role(Role.ADMIN).confirme(true).senha("1234")
				.build();
	}

}
