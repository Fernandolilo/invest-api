package com.invest.utils;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.invest.dto.ApiLogDTO;
import com.invest.service.ApiLogService;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class ApiLoggingFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(ApiLoggingFilter.class);

    private final MeterRegistry meterRegistry;
    private final ApiLogService apiLogService;

    public ApiLoggingFilter(MeterRegistry meterRegistry, ApiLogService apiLogService) {
        this.meterRegistry = meterRegistry;
        this.apiLogService = apiLogService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest requestRaw,
                                    HttpServletResponse responseRaw,
                                    FilterChain filterChain) throws ServletException, IOException {

        long startTime = System.currentTimeMillis();

        // Envolver request e response
        ContentCachingRequestWrapper request = new ContentCachingRequestWrapper(requestRaw);
        ContentCachingResponseWrapper response = new ContentCachingResponseWrapper(responseRaw);

        try {
            filterChain.doFilter(request, response);
        } finally {
            long duration = System.currentTimeMillis() - startTime;

            // Captura request body
            String requestBody = "";
            byte[] requestBuf = request.getContentAsByteArray();
            if (requestBuf.length > 0) {
                try {
                    requestBody = new String(requestBuf, request.getCharacterEncoding());
                    // Mascarar password
                    requestBody = requestBody.replaceAll(
                    	    "\"(password|senha)\"\\s*:\\s*\".*?\"",  // campos password ou senha
                    	    "\"$1\":\"****\""                        // mantém o nome do campo, mas mascara o valor
                    	);

                } catch (Exception e) {
                    logger.warn("Erro ao ler request body", e);
                }
            }

            // Captura response body (opcional)
            String responseBody = "";
            byte[] responseBuf = response.getContentAsByteArray();
            if (responseBuf.length > 0) {
                try {
                    responseBody = new String(responseBuf, response.getCharacterEncoding());
                } catch (Exception e) {
                    logger.warn("Erro ao ler response body", e);
                }
            }

            // Criar DTO de log
            ApiLogDTO log = new ApiLogDTO();
            log.setMethod(request.getMethod());
            log.setPath(request.getRequestURI());
            log.setStatus(response.getStatus());
            log.setDurationMs(duration);
            log.setIp(request.getRemoteAddr());
            log.setRequestBody(requestBody);
            log.setTimestamp(LocalDateTime.now());

            // Salvar log no serviço
            apiLogService.addLog(log);

            // Log no console
            logger.info(log.toString());

            // Métricas Prometheus
            Timer.builder("api_requests_duration_ms")
                 .description("Duração das requisições HTTP em ms")
                 .tag("method", log.getMethod())
                 .tag("path", log.getPath())
                 .tag("status", String.valueOf(log.getStatus()))
                 .register(meterRegistry)
                 .record(Duration.ofMillis(duration));

            Counter.builder("api_requests_total")
                   .description("Número total de requisições HTTP")
                   .tag("method", log.getMethod())
                   .tag("path", log.getPath())
                   .tag("status", String.valueOf(log.getStatus()))
                   .register(meterRegistry)
                   .increment();

            // Copiar o corpo da response de volta para o cliente
            response.copyBodyToResponse();
        }
    }
}
