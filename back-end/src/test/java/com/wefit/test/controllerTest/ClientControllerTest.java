package com.wefit.test.controllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wefit.test.controller.ClientController;
import com.wefit.test.entity.Client;
import com.wefit.test.entity.dto.AuthenticationDTO;
import com.wefit.test.entity.dto.ClientDTO;
import com.wefit.test.entity.dto.ClientNewDTO;
import com.wefit.test.entity.dto.EnderecoNewDTO;
import com.wefit.test.entity.dto.requests.ClientRequest;
import com.wefit.test.entity.enums.Perfil;
import com.wefit.test.entity.enums.TipoPessoa;
import com.wefit.test.service.ClientService;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(ClientController.class)
@AutoConfigureMockMvc
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
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	

	private final static String API = "/clients";

	 @BeforeEach
	    public void authenticate() throws Exception {
	        AuthenticationDTO auth = AuthenticationDTO.builder()
	                .email("fernando@wefit.com.br")
	                .password("1234")
	                .build();

	        String authJson = new ObjectMapper().writeValueAsString(auth);

	        MvcResult loginResult = mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
	                        .contentType(MediaType.APPLICATION_JSON)
	                        .content(authJson))
	                .andExpect(status().isOk())
	                .andReturn();

	        String responseContent = loginResult.getResponse().getContentAsString();
	        String tokentoken = new ObjectMapper().readTree(responseContent).get("token").asText();
	    }

	@Test
	public void save() throws Exception {
		
		   String jsonToken = token;

	        mockMvc.perform(MockMvcRequestBuilders.post(API+"/authenticate")
	                        .header("Authorization", "Bearer " + token)
	                        .contentType(MediaType.APPLICATION_JSON)
	                        .content(jsonToken))
	                .andExpect(status().isCreated());
	
		
		newClientDto();

		enderecoNewDTO();

		// entity save
		Client entityCli = client();

		// entity save

		// objeto de retorno
		ClientDTO entityDTO = clientDTO();

		BDDMockito.given(mapper.map(Mockito.any(ClientNewDTO.class), Mockito.eq(Client.class))).willReturn(entityCli);

		BDDMockito.given(service.save(cli, end)).willReturn(entityDTO);

		// objeto de trasferencia de dados
		ClientRequest clientRequest = ClientRequest.builder().client(cli).endereco(end).build();

		// json a ser enviado para post.
		String json = new ObjectMapper().writeValueAsString(clientRequest);

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(json);

		// Executar o teste
		mockMvc.perform(request).andExpect(status().isCreated()) // Espera status HTTP 201
		; 

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

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(json);

		mockMvc.perform(request).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.erros[0].campo").value("client.cpfOuCnpj"))
				.andExpect(jsonPath("$.erros[0].mensagem").value("CPF inválido"));
	}

	private ClientDTO clientDTO() {
		ClientDTO entityDTO = ClientDTO.builder().nome(cli.getNome()).cpfOuCnpj(cli.getCpfOuCnpj())
				.celular(cli.getCelular()).email(cli.getEmail()).telefone(cli.getTelefone()).tipo(cli.getTipo())
				.confirme(cli.isConfirme()).senha(cli.getSenha()).build();
		return entityDTO;
	}

	private Client client() {
		Client entityCli = Client.builder().nome(cli.getNome()).cpfOuCnpj(cli.getCpfOuCnpj()).celular(cli.getCelular())
				.email(cli.getEmail()).telefone(cli.getTelefone()).tipo(cli.getTipo()).perfil(cli.getPerfil())
				.confirme(cli.isConfirme()).senha(cli.getSenha()).build();
		return entityCli;
	}

	private void newClientDto() {
		cli = ClientNewDTO.builder().nome("Fernando").cpfOuCnpj("93906787060").celular("11 1234-14567")
				.email("fernando@wefit.com.br").telefone("11 12345678").tipo(TipoPessoa.PESSOA_FISICA)
				.perfil(Perfil.COMPRADOR).confirme(true).senha("1234").build();
	}

	private void enderecoNewDTO() {
		end = EnderecoNewDTO.builder().cep("12345.123").logradouro("Rua Faria Lima").numero("123")
				.complemento("Predio A").bairro("Pinheiros").cidade("São Paulo").estado("SP").build();
	}

	private void cliNewDtoCpfInvalid() {
		cli = ClientNewDTO.builder().nome("Fernando").cpfOuCnpj("123456789") // CPF inválido
				.celular("11 1234-14567").email("fernando@wefit.com.br").telefone("11 12345678")
				.tipo(TipoPessoa.PESSOA_FISICA).perfil(Perfil.COMPRADOR).confirme(true).senha("1234").build();
	}

}
