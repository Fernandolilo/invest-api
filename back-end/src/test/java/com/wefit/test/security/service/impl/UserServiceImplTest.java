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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import com.invest.entity.Client;
import com.invest.entity.enums.Role;
import com.invest.reposiotries.ClientRepository;
import com.invest.sercurity.service.UserSecurityDetails;
import com.invest.sercurity.service.impl.UserDetailsServiceImpl;
import com.invest.service.exeptions.AuthorizationException;

@ActiveProfiles("test")	
@ExtendWith(MockitoExtension.class) // Usando Mockito com JUnit 5
public class UserServiceImplTest {

	@InjectMocks
	private UserDetailsServiceImpl userDetailsService;

	@Mock
	private ClientRepository clientRepository;

	private Client client;

	@BeforeEach
	public void setUp() {
		// Configuração inicial para os testes
		client = new Client();
		client.setId(UUID.randomUUID()); // Certifique-se de atribuir um ID único
		client.setEmail("test@example.com");
		client.setSenha("password123");
		client.setRoles(new HashSet<>());
		client.getRoles().add(Role.USER);
	}

	@Test
	public void testLoadUserByUsername_UserFound() {
		// Configurando o comportamento do mock para retornar o client quando o email é
		// passado
		when(clientRepository.findByEmail("test@example.com")).thenReturn(Optional.of(client));

		// Chamando o método que queremos testar
		UserSecurityDetails userDetails = (UserSecurityDetails) userDetailsService
				.loadUserByUsername("test@example.com");

		// Verificando os resultados
		assertNotNull(userDetails);
		assertEquals(client.getId(), userDetails.getId()); // Comparando o ID correto
		assertEquals("test@example.com", userDetails.getUsername());
		assertEquals("password123", userDetails.getPassword());
	}

	 @Test
	    public void testLoadUserByUsername_UserNotFound() {
	        // Configurando o mock para retornar um Optional vazio quando o email não for encontrado
	        when(clientRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

	        // Chamando o método que deve lançar a exceção
	        assertThrows(AuthorizationException.class, () -> {
	            userDetailsService.loadUserByUsername("test@example.com");
	        });
	    }
	
	
}
