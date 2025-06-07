package com.invest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.invest.service.StartDB;

import lombok.RequiredArgsConstructor;

@Configuration
@Profile("dev")
@RequiredArgsConstructor
public class DevConfig {
	
	private final StartDB start;

	@Bean
	public boolean init() {
		start.initDB();
		return true;
	}
}
