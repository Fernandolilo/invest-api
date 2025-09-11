package com.syp.gateway.config;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class LoggingFilter implements GlobalFilter, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String requestId = UUID.randomUUID().toString();
        long startTime = System.currentTimeMillis();

        var request = exchange.getRequest();

        // captura IP (X-Forwarded-For ou remoto)
        String clientIp = request.getHeaders().getFirst("X-Forwarded-For");
        if (clientIp == null && request.getRemoteAddress() != null) {
            clientIp = request.getRemoteAddress().getAddress().getHostAddress();
        }

        logger.info("[REQ {}] {} {} from {}", requestId, request.getMethod(), request.getURI(), clientIp);

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            long duration = System.currentTimeMillis() - startTime;
            var response = exchange.getResponse();
            logger.info("[RES {}] Status={} Duration={}ms", requestId, response.getStatusCode(), duration);
        }));
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
