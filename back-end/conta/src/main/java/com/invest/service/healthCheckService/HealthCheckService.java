package com.invest.service.healthCheckService;

import java.sql.Connection;

import javax.sql.DataSource;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class HealthCheckService {

    private final DataSource dataSource;
    private final CachingConnectionFactory rabbitConnectionFactory;

    public boolean checkDatabase() {
        try (Connection connection = dataSource.getConnection()) {
            boolean isValid = connection.isValid(2);
            if (!isValid) {
                log.error("Database connection inválida.");
            }
            return isValid;
        } catch (Exception e) {
            log.error("Erro ao verificar Database", e);
            return false;
        }
    }

    public boolean checkRabbit() {
        try (org.springframework.amqp.rabbit.connection.Connection connection =
                     rabbitConnectionFactory.createConnection()) {

            boolean open = connection.isOpen();

            if (!open) {
                log.error("Conexão RabbitMQ fechada.");
            }

            return open;

        } catch (Exception e) {
            log.error("RabbitMQ indisponível", e);
            return false;
        }
    }
}
