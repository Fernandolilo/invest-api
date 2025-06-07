package com.wefit.test.repositoryTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.invest.entity.Client;
import com.invest.entity.Endereco;
import com.invest.entity.enums.TipoPessoa;
import com.invest.reposiotries.ClientRepository;
import com.invest.reposiotries.EnderecoRepository;
import com.invest.service.exeptions.ObjectNotFoundException;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class ClientRepositoryTest {

	@Autowired
	TestEntityManager entityManager;

	@Autowired
	ClientRepository repository;

	@Autowired
	EnderecoRepository enderecoRepository;

	private Client cli;
	private Endereco end;

	@BeforeEach
	public void setUp() {
		// Inicializa os dados para o teste
		cli = Client.builder().nome("Fernando").cpfOuCnpj("12345678912").celular("11 1234-14567")
				.email("fernando@wefit.com.br").telefone("11 12345678").tipo(TipoPessoa.PESSOA_FISICA).confirme(true)
				.senha("1234").build();

		end = Endereco.builder().cep("12345.123").logradouro("Rua Faria Lima").numero("123").complemento("Predio A")
				.bairro("Pinheiros").cidade("São Paulo").estado("SP").client(cli).build();
	}

	@Test
	@DisplayName("Save client")
	public void saveClient() {

		entityManager.persist(cli);
		entityManager.flush();

		cli.setEndereco(end);

		entityManager.persist(end);
		entityManager.flush();

		// Verifica se o cliente foi persistido
		Client savedClient = repository.findById(cli.getId())
				.orElseThrow(() -> new ObjectNotFoundException("Client not found with id: " + cli.getId()));
		assertNotNull(savedClient);
		assertEquals("Fernando", savedClient.getNome());

		// Verifica se o endereço foi persistido corretamente
		Endereco savedEndereco = enderecoRepository.findById(end.getId())
				.orElseThrow(() -> new ObjectNotFoundException("Client not found with id: " + cli.getId()));
		assertNotNull(savedEndereco);
		assertEquals("Rua Faria Lima", savedEndereco.getLogradouro());
		assertEquals("São Paulo", savedEndereco.getCidade());

	}

}
