package com.invest.filter;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class HeaderExposureFIlter extends OncePerRequestFilter {
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	        throws ServletException, IOException {

	    // Cabeçalhos CORS
	    //response.setHeader("Access-Control-Allow-Origin", "http://38.210.209.86:8080"); // Permite qualquer origem
	    response.setHeader("Access-Control-Allow-Origin", "https://localhost:4200"); // Permite qualquer origem
	    response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
	    response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, xsrf-token");
	    response.setHeader("Access-Control-Expose-Headers", "Authorization, xsrf-token");
	    response.setHeader("Access-Control-Allow-Credentials", "true"); // Permite credenciais
	    response.setHeader("Access-Control-Max-Age", "3600");

	    // Tratando requisição OPTIONS
	    if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
	        response.setStatus(HttpServletResponse.SC_OK);
	    } else {
	        filterChain.doFilter(request, response);
	    }
	}

}
