package com.invest.repositoryTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.invest.entity.Client;
import com.invest.entity.Conta;
import com.invest.entity.enums.Role;
import com.invest.entity.enums.TipoConta;
import com.invest.entity.enums.TipoPessoa;
import com.invest.reposiotries.ContaRepository;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class ContaRepositoryTest {
	@Autowired
	private ContaRepository repository;

	@Autowired
	TestEntityManager entityManager;


	@Test
	@DisplayName("Deve retornar o maior numero de conta")
	void deveRetornarMaiorNumeroParaConta() {
		Client cli = clientPersistence();

		entityManager.persist(cli);
		entityManager.flush();

		Conta conta = contaPersistence(cli);

		entityManager.persist(conta);
		entityManager.flush();

		Optional<Integer> maiorNumero = repository.findMaxConta();
		Integer proximo = repository.findMaxConta().orElse(0) + 1;
		assertThat(maiorNumero).isPresent();
		assertThat(proximo).isEqualTo(1002);
	}

	@Test
	@DisplayName("Deve retornar o cliente pelo CPF ou CNPJ da conta")
	void deveRetornarClientePeloCpfOuCnpjDaConta() {
		Client cli = clientPersistence();
		entityManager.persist(cli);
		entityManager.flush();

		Conta conta = contaPersistence(cli);

		entityManager.persist(conta);
		entityManager.flush();

		Optional<Client> foundCpfOuCnpj = repository.findClienteByContaCpfouCnpj("12312312311");
		
		assertThat(foundCpfOuCnpj).isPresent();
		assertThat(foundCpfOuCnpj.get().getEmail()).isEqualTo("fernando@gmail.com");
	}
	
	@Test
	@DisplayName("Deve retornar Optional vazio quando cliente não for encontrado pelo CPF/CNPJ")
	void deveRetornarOptionalVazioQuandoClienteNaoExistir() {
	    Optional<Client> foundCpfOuCnpj = repository.findClienteByContaCpfouCnpj("00000000000");
	    assertThat(foundCpfOuCnpj).isEmpty();
	}
	
	
	@Test
	@DisplayName("Deve retornar uma lista de conta pelos cnpj/cpf")
	void deveRetornarListaClientePeloCpfOuCnpjDaConta() {
		Client cli = clientPersistence();

		entityManager.persist(cli);
		entityManager.flush();

		Conta conta = contaPersistence(cli);

		entityManager.persist(conta);
		entityManager.flush();

		List<Conta> contas = repository.findAllByClientId(cli.getId());
		
		
		assertThat(contas).isNotEmpty();
		
		assertThat(contas).contains(conta);		

		assertThat(contas).anySatisfy(c ->
	    assertThat(conta.getClient().getNome()).isEqualTo("Fernando")
	);

		   
		
	}
	
	@Test
	@DisplayName("Deve Retornar Conta Por Id do Cliente")
	void deveRetornarVazioContaPorIdDoCliente() {
		
		Client cli = clientPersistence();
		
		Optional<Conta> foundConta = repository.findByClientId(cli.getId());
		
		assertThat(foundConta).isEmpty();	
		
	}
	
	@Test
	@DisplayName("Deve Retornar Conta Por Id do Cliente")
	void deveRetornarContaPorIdDoCliente() {
		
		Client cli = clientPersistence();

		entityManager.persist(cli);
		entityManager.flush();

		Conta conta = contaPersistence(cli);

		entityManager.persist(conta);
		entityManager.flush();
		
		Optional<Conta> foundConta = repository.findByClientId(cli.getId());
		
		assertThat(foundConta).isPresent();
		assertThat(foundConta.get().getClient().getNome()).isEqualTo("Fernando");
		
	}

	private Client clientPersistence() {
		Client cli = Client.builder().nome("Fernando").cpfOuCnpj("12312312311").celular("1112345134")
				.telefone("1234-1234").email("fernando@gmail.com").tipo(TipoPessoa.PESSOA_FISICA).role(Role.ADMIN)
				.senha("1234").confirme(true).build();
		return cli;
	}

	private Conta contaPersistence(Client cli) {
		Conta conta = Conta.builder().agencia(1000).banco(237).client(cli).cpfOuCnpj("12312312311").numero(1001).tipo(TipoConta.CONTA_CORRENTE)
				.saldo(BigDecimal.valueOf(150.0)).build();
		return conta;
	}
}
