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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.invest.dto.AuthenticationDTO;
import com.invest.dto.ClientNewDTO;
import com.invest.dto.EnderecoNewDTO;
import com.invest.sercurity.jwt.JwtAuthenticationFilter;
import com.invest.sercurity.jwt.JwtService;
import com.invest.service.ClientService;
import com.invest.utils.valid.ClientInsert;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@ClientInsert
public class ContaControllerTest {
	
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

	private final static String API = "/contas";
	
	
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
		AuthenticationDTO auth = AuthenticationDTO.builder().email("fernando.nandotania@hotmail.com").password("1234").build();
		return auth;
	}
}
