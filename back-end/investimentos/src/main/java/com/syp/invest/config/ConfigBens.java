package com.syp.invest.config;

import java.net.http.HttpClient;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ConfigBens {

	@Bean
	public ModelMapper mapper() {
		return new ModelMapper();
	}
	
	@Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
	
	@Bean
    public HttpClient httpClient() {
        return HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2) // usa HTTP/2 quando disponível
                .build();
    }
	
}
