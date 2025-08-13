package com.invest.infraestrutura;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.invest.dto.ContaNewDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContaMessageProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${invest.rabbitmq.exchange}")
    private String exchange;

    @Value("${invest.rabbitmq.routingKey}")
    private String routingKey;

    public void sendMessage(ContaNewDTO conta) {
        try {
            rabbitTemplate.convertAndSend(exchange, routingKey, conta);
            System.out.println(" Mensagem enviada para o RabbitMQ: " + conta);
        } catch (Exception e) {
            System.err.println("Erro ao enviar mensagem para o RabbitMQ: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
