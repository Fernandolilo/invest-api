package com.invest.controllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
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
import com.invest.dto.ContaNewDTO;
import com.invest.entity.Client;
import com.invest.entity.Conta;
import com.invest.entity.enums.TipoConta;
import com.invest.sercurity.jwt.JwtAuthenticationFilter;
import com.invest.sercurity.jwt.JwtService;
import com.invest.service.AuthService;
import com.invest.service.ClientService;
import com.invest.service.ContaService;
import com.invest.utils.valid.ClientInsert;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@ClientInsert
public class ContaControllerTest {

	@Autowired
	private MockMvc mockMvc; // Mock do MockMvc para testar os endpoints

	@MockBean
	private ContaService service; // Mock do 

	@MockBean
	private AuthService authService; // Mock do serviço

	@MockBean
	private ClientService clientervice; // Mock do serviço

	@MockBean
	private ModelMapper mapper;

	private Client client;
	private Conta conta;
	private ContaNewDTO contaDTO;

	private String token;
	@MockBean
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@MockBean
	private JwtService jwtService;

	@MockBean
	private AuthenticationManager authenticationManager;

	private final static String API = "/contas";

	@Test
	void shouldAuthenticateAndAddAuthorizationHeader() throws Exception {
		AuthenticationDTO auth = authentication();

		String expectedToken = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
		String json = new ObjectMapper().writeValueAsString(auth);

		// Garantir que o serviço gera o token correto
		BDDMockito.given(authService.fromAuthentication(Mockito.any(AuthenticationDTO.class)))
				.willReturn(expectedToken);

		mockMvc.perform(MockMvcRequestBuilders.post(API + "/authenticate").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk());
		// .andExpect(header().string("Authorization", "Bearer " + expectedToken));

	}

	@Test
	@DisplayName("save")
	void save() throws Exception {

		// DTO de entrada
		contaDTO = ContaNewDTO.builder().agencia(1001).agencia(237).numero(123456).saldo(100.0)
				.tipo(TipoConta.CONTA_CORRENTE).build();

		// Conta mapeada
		conta = Conta.builder().id(UUID.randomUUID()).agencia(1001).banco(237).numero(123456).saldo(1000.0)
				.tipo(TipoConta.CONTA_CORRENTE).client(client).build();

		BDDMockito.given(mapper.map(Mockito.any(ContaNewDTO.class), Mockito.eq(Conta.class))).willReturn(conta);
		BDDMockito.given(service.save(contaDTO)).willReturn(conta);

		String json = new ObjectMapper().writeValueAsString(contaDTO);

		// Mock do token (substitua pelo token que você deseja testar)
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmZXJuYW5kb0B3ZWZpdC5jb20uYnIiLCJpYXQiOjE3NDYzODAwODMsImV4cCI6MTc0NjM4MDI2M30.zHSJhEInPeaPkhcHIWZjmPUR_5nzIZoKGJ4Yq80sgAY";

		// Criar o request incluindo o cabeçalho Authorization
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(json).header("Authorization", "Bearer " + token); 
	}
	
	
	

	private AuthenticationDTO authentication() {
		// Arrange
		AuthenticationDTO auth = AuthenticationDTO.builder().email("fernando.nandotania@hotmail.com").password("1234")
				.build();
		return auth;
	}
}
