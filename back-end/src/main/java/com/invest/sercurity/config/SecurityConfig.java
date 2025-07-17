package com.invest.sercurity.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.invest.sercurity.jwt.JwtAuthenticationFilter;
import com.invest.sercurity.service.impl.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	private static final String API_URL_CLIENTS_AUTH = "/clients/authenticate/**";
	private static final String API_URL_CLIENTS_SAVE= "/clients/**";
	private static final String API_CDI = "/cdi/**";
	
	
	private final JwtAuthenticationFilter auth;
	private final Environment env;

	public SecurityConfig(JwtAuthenticationFilter auth, Environment env) {
		this.auth = auth;
		this.env = env;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		// Test environment
		if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.authorizeHttpRequests(authz -> authz.requestMatchers("/h2-console/**").permitAll()
					.requestMatchers("/swagger-ui/**").permitAll())
					.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**", "/swagger-ui/**"))
					.headers(headers -> headers.frameOptions().sameOrigin());
		}

		// Test environment
		if (Arrays.asList(env.getActiveProfiles()).contains("dev")) {
			http.authorizeHttpRequests(authz -> authz.requestMatchers("/swagger-ui/**").permitAll())
					.csrf(csrf -> csrf.ignoringRequestMatchers("/swagger-ui/**"))
					.headers(headers -> headers.frameOptions().sameOrigin());
		}

		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(
						authz -> authz
						.requestMatchers(API_URL_CLIENTS_AUTH).permitAll()
						.requestMatchers(API_URL_CLIENTS_SAVE).permitAll()
						.requestMatchers(API_CDI).permitAll()
						.anyRequest().authenticated())
				.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(auth, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();

		// Permitindo credenciais
		config.setAllowCredentials(true);

		//config.addAllowedOrigin("http://38.210.209.86:8080");
		config.addAllowedOrigin("http://localhost:4200");
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

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
		return authenticationProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers("/v3/api-docs/**");
	}
}
