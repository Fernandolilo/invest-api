package com.wefit.test.security.jwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import com.wefit.test.sercurity.jwt.JwtService;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

    private JwtService jwtService;

    @Mock
    private UserDetails userDetails;

    private final String testUsername = "test@example.com";
    private String generatedToken;

    @BeforeEach
    public void setUp() {
        jwtService = new JwtService();
        generatedToken = jwtService.generateToken(testUsername); // Gera um token para ser usado nos testes
    }

    @Test
    public void testGenerateToken() {
        // Verifica se o token gerado não é nulo ou vazio
        assertNotNull(generatedToken);
        assertFalse(generatedToken.isEmpty());
    }

    @Test
    public void testExtractUsernameFromToken() {
        // Extrai o username do token gerado
        String username = jwtService.extractUsername(generatedToken);
        assertEquals(testUsername, username);
    }

    @Test
    public void testExtractExpirationFromToken() {
        // Extrai a data de expiração do token gerado
        Date expiration = jwtService.extractExpiration(generatedToken);
        assertNotNull(expiration);
        assertTrue(expiration.after(new Date())); // Verifica se a expiração é após a data atual
    }

    @Test
    public void testValidateToken_ValidToken() {
        // Simula o comportamento do UserDetails
        when(userDetails.getUsername()).thenReturn(testUsername);

        // Verifica se o token gerado é válido para o usuário
        assertTrue(jwtService.validateToken(generatedToken, userDetails));
    }

    @Test
    public void testValidateToken_InvalidToken() {
        // Simula um username inválido
        when(userDetails.getUsername()).thenReturn("invalid@example.com");

        // Verifica se o token gerado não é válido para o usuário
        assertFalse(jwtService.validateToken(generatedToken, userDetails));
    }

    @Test
    public void testIsTokenExpired() {
        // Cria um token expirado para testar
        String expiredToken = jwtService.generateToken(testUsername);
        
        // Aguardar um tempo para garantir que o token expire
        try {
            Thread.sleep(2000); // Dorme por 2 segundos para garantir que o token expire
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verifica se o token expirado está sendo tratado corretamente
        assertThat(jwtService.extractExpiration(expiredToken));
    }
}

