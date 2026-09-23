package com.invest.taskServices;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.invest.service.healthCheckService.HealthCheckService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class LogMonitoringTask {

    private final HealthCheckService healthCheckService;

    @Scheduled(fixedRate = 60000)
    public void monitoramentoAutomatico() {

        boolean bancoOk = healthCheckService.checkDatabase();
        boolean rabbitOk = healthCheckService.checkRabbit();

        if (bancoOk && rabbitOk) {
            log.info("✅ Sistema saudável. Banco e RabbitMQ OK.");
            return;
        }

        if (!bancoOk) {
            log.error("⚠️ ALERTA: Banco de dados está DOWN!");
        }

        if (!rabbitOk) {
            log.error("⚠️ ALERTA: RabbitMQ está DOWN!");
        }
    }
}

