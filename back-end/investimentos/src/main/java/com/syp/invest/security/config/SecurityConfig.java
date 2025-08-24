package com.syp.invest.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration

public class SecurityConfig {

	

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();

		// Permitindo credenciais
		config.setAllowCredentials(true);

		//config.addAllowedOrigin("http://38.210.209.86:8080");
		config.addAllowedOrigin("http://localhost:8765");
		// Permitindo todos os cabeçalhos
		config.addAllowedHeader("*");

		// Permitindo métodos GET, POST, PUT, DELETE, OPTIONS
		config.addAllowedMethod("GET");
		config.addAllowedMethod("POST");
		config.addAllowedMethod("PUT");
		config.addAllowedMethod("DELETE");
		config.addAllowedMethod("OPTIONS");

		// Registrando a configuração de CORS
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}

	
}
