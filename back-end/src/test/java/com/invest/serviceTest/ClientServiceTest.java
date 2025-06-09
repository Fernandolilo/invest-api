package com.invest.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.invest.entity.Client;
import com.invest.entity.Endereco;
import com.invest.entity.enums.TipoPessoa;
import com.invest.reposiotries.ClientRepository;
import com.invest.reposiotries.EnderecoRepository;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class ClientServiceTest {

	@MockBean
	private ClientRepository clientRepository;

	@MockBean
	private EnderecoRepository enderecoRepository;

	private Client cli;
	private Endereco end;

	@BeforeEach
	public void setUp() {
		cli = Client.builder().nome("Fernando").cpfOuCnpj("12345678912").celular("11 1234-14567")
				.email("fernando@wefit.com.br").telefone("11 12345678").tipo(TipoPessoa.PESSOA_FISICA).confirme(true)
				.senha("1234").build();

		// Cria o endereço
		end = Endereco.builder().cep("12345.123").logradouro("Rua Faria Lima").numero("123").complemento("Predio A")
				.bairro("Pinheiros").cidade("São Paulo").estado("SP").client(cli).build();
		cli.setEndereco(end);

	}

	@Test
    @DisplayName("Save client")
    public void save() {        
        // Configura o ID para os objetos simulados
        UUID clientId = UUID.randomUUID();
        cli.setId(clientId);
        UUID enderecoId = UUID.randomUUID();
        end.setId(enderecoId);

        // Simula o comportamento dos repositórios
        when(clientRepository.save(cli)).thenReturn(cli);
        when(enderecoRepository.save(end)).thenReturn(end);
        
        // Realiza a persistência
        clientRepository.save(cli);  // Chama o método para salvar o cliente
        enderecoRepository.save(end);  // Chama o método para salvar o endereço
        
        // Verifica se os métodos de persistência foram chamados corretamente
        verify(clientRepository, times(1)).save(cli);  // Verifica se save foi chamado uma vez para o cliente
        verify(enderecoRepository, times(1)).save(end);  // Verifica se save foi chamado uma vez para o endereço

        // Verifica se os objetos não são nulos após a persistência
        assertNotNull(cli.getId());  // Verifica se o cliente tem um ID (indicando que foi persistido)
        assertNotNull(end.getId());  // Verifica se o endereço tem um ID (indicando que foi persistido)
    }

	@Test
	@DisplayName("Find client by ID")
	public void findById() {
		// Definir o comportamento dos mocks para a busca por ID
		when(clientRepository.findById(cli.getId())).thenReturn(java.util.Optional.of(cli));
		when(enderecoRepository.findById(end.getId())).thenReturn(java.util.Optional.of(end));

		// Buscar os dados
		Client foundClient = clientRepository.findById(cli.getId()).orElse(null);
		Endereco foundEndereco = enderecoRepository.findById(end.getId()).orElse(null);

		// Verificar se os objetos encontrados não são nulos
		assertNotNull(foundClient);
		assertNotNull(foundEndereco);

		// Verificar se o conteúdo é o esperado
		assertEquals("Fernando", foundClient.getNome());
		assertEquals("Rua Faria Lima", foundEndereco.getLogradouro());
	}

}
