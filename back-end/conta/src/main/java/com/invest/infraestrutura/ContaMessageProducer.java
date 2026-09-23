package com.invest.infraestrutura;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.invest.dto.ContaNewDTO;
import com.invest.dto.ContaUpdateDTO;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ContaMessageProducer {

    private final RabbitTemplate rabbitTemplate;
    private final CircuitBreaker rabbitCircuitBreaker;

    public static final String CONTA_EXCHANGE = "conta.exchange";
    public static final String ROUTING_KEY_CONTACC = "conta.cc";

    public void sendMessage(ContaNewDTO conta) {
        try {
            // executa diretamente pelo CircuitBreaker
            rabbitCircuitBreaker.executeRunnable(() ->
                    rabbitTemplate.convertAndSend(CONTA_EXCHANGE, ROUTING_KEY_CONTACC, conta)
            );
        } catch (CallNotPermittedException ex) {
            System.out.println("⚠️ RabbitMQ indisponível (CircuitBreaker aberto)");
        } catch (Exception e) {
            System.out.println("Erro ao enviar mensagem para RabbitMQ: " + e.getMessage());
        }
    }

    public void sendUpdateMessage(ContaUpdateDTO conta) {
        try {
            rabbitCircuitBreaker.executeRunnable(() ->
                    rabbitTemplate.convertAndSend(CONTA_EXCHANGE, ROUTING_KEY_CONTACC, conta)
            );
        } catch (CallNotPermittedException ex) {
            System.out.println("⚠️ RabbitMQ indisponível (CircuitBreaker aberto)");
        } catch (Exception e) {
            System.out.println("Erro ao enviar atualização para RabbitMQ: " + e.getMessage());
        }
    }
}
