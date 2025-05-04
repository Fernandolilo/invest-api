package com.wefit.test.security.config;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.wefit.test.sercurity.jwt.JwtAuthenticationFilter;

@TestConfiguration
public class TestSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()  // Desabilita CSRF
            .authorizeRequests()
                .requestMatchers("/clients/authenticate").permitAll()  // Permite acesso sem autenticação
                //.requestMatchers("/clients/protected").authenticated()  // Protege o endpoint '/protected'
                .anyRequest().permitAll()
                .anyRequest().authenticated()
                ;  // Permite todas as outras requisições
        return http.build();
    }
    
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return Mockito.mock(JwtAuthenticationFilter.class);  // Mock do filtro de autenticação
    }
}
