package com.syp.invest.infraestrutura;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.syp.invest.entity.dto.ContaUpdateDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ContaMessageProducer {

    private final RabbitTemplate rabbitTemplate;

    public static final String CONTA_EXCHANGE = "conta.exchange";

    public static final String ROUTING_KEY_RENDAFIXA = "rendafixa.cc";

 
	
	public void sendMessage(ContaUpdateDTO conta) {
		rabbitTemplate.convertAndSend(CONTA_EXCHANGE, ROUTING_KEY_RENDAFIXA, conta);
	     System.out.println("Messangem enviada com sucesso");
    }
}