package com.invest.utils;

import java.time.Duration;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.invest.dto.ApiLogDTO;
import com.invest.service.ApiLogService;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Timer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class ApiLoggingFilter implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(ApiLoggingFilter.class);

    private final MeterRegistry meterRegistry;
    private final ApiLogService apiLogService;

    public ApiLoggingFilter(MeterRegistry meterRegistry, ApiLogService apiLogService) {
        this.meterRegistry = meterRegistry;
        this.apiLogService = apiLogService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        request.setAttribute("startTime", System.currentTimeMillis());

        // Envolver a requisição apenas se ainda não for ContentCachingRequestWrapper
        if (!(request instanceof ContentCachingRequestWrapper)) {
            request = new ContentCachingRequestWrapper(request);
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest requestRaw, HttpServletResponse response, Object handler, Exception ex) {
        long start = (long) requestRaw.getAttribute("startTime");
        long duration = System.currentTimeMillis() - start;

        String requestBody = "";

        // Evita ClassCastException
        ContentCachingRequestWrapper request;
        if (requestRaw instanceof ContentCachingRequestWrapper) {
            request = (ContentCachingRequestWrapper) requestRaw;
            byte[] buf = request.getContentAsByteArray();
            if (buf.length > 0) {
                try {
                    requestBody = new String(buf, request.getCharacterEncoding());
                    // Mascarar password
                    requestBody = requestBody.replaceAll("\"password\"\\s*:\\s*\".*?\"", "\"password\":\"****\"");
                } catch (Exception e) {
                    logger.warn("Erro ao ler request body", e);
                }
            }
        }

        // Criar DTO de log
        ApiLogDTO log = new ApiLogDTO();
        log.setMethod(requestRaw.getMethod());
        log.setPath(requestRaw.getRequestURI());
        log.setStatus(response.getStatus());
        log.setDurationMs(duration);
        log.setIp(requestRaw.getRemoteAddr()); // IP externo
        log.setRequestBody(requestBody);
        log.setTimestamp(LocalDateTime.now());

        // Salvar log
        apiLogService.addLog(log);

        // Log no console
        if (ex != null) {
            logger.error(log.toString(), ex);
        } else {
            logger.info(log.toString());
        }

        // Métricas Prometheus
        // 1️⃣ Duração da requisição
        Timer.builder("api_requests_duration_ms")
             .description("Duração das requisições HTTP em ms")
             .tag("method", log.getMethod())
             .tag("path", log.getPath())
             .tag("status", String.valueOf(log.getStatus()))
             .register(meterRegistry)
             .record(Duration.ofMillis(duration));

        // 2️⃣ Contagem de requisições
        Counter.builder("api_requests_total")
               .description("Número total de requisições HTTP")
               .tag("method", log.getMethod())
               .tag("path", log.getPath())
               .tag("status", String.valueOf(log.getStatus()))
               .register(meterRegistry)
               .increment();
    }
}
