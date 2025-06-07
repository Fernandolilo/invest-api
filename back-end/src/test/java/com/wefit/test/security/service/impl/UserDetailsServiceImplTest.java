package com.wefit.test.security.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import com.invest.entity.Client;
import com.invest.reposiotries.ClientRepository;
import com.invest.sercurity.service.impl.UserDetailsServiceImpl;
import com.invest.service.exeptions.AuthorizationException;

@ActiveProfiles("test")
public class UserDetailsServiceImplTest {

	@Mock
	private ClientRepository clientRepository;

	@InjectMocks
	private UserDetailsServiceImpl userDetailsServiceImpl;

	private Client client;
	private String email = "test@example.com";

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);

		client = new Client();
		client.setId(UUID.randomUUID());
		client.setEmail(email);
		client.setSenha("123456");
		client.setRoles(new HashSet<>());
	}

	@Test
	public void testLoadUserByUsername_Success() {
		// Mockando o comportamento do repositório
		when(clientRepository.findByEmail(email)).thenReturn(Optional.of(client));

		// Testando a carga do usuário
		UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(email);

		assertNotNull(userDetails);
		assertEquals(email, userDetails.getUsername());
	}

	@Test
	public void testLoadUserByUsername_ClientNotFound() {
		// Mockando a situação em que o cliente não é encontrado
		when(clientRepository.findByEmail(email)).thenReturn(Optional.empty());

		// Verificando se a exceção ocorre
		assertThrows(AuthorizationException.class, () -> {
			userDetailsServiceImpl.loadUserByUsername(email);
		});
	}

	@Test
	public void testLoadUserByUsername_UsernameNotFound() {
	    // Mockando o repositório para retornar um Optional vazio
	    when(clientRepository.findByEmail(email)).thenReturn(Optional.empty());

	    // Testando a exceção AuthorizationException ao invés de UsernameNotFoundException
	    assertThrows(AuthorizationException.class, () -> {
	        userDetailsServiceImpl.loadUserByUsername(email);
	    });
	}

}
