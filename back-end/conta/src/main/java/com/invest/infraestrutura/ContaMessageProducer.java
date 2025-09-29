package com.invest.infraestrutura;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.invest.dto.ContaDTO;
import com.invest.dto.ContaNewDTO;
import com.invest.dto.ContaUpdateDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ContaMessageProducer {

	private final RabbitTemplate rabbitTemplate;

	public static final String CONTA_EXCHANGE = "conta.exchange";

	public static final String ROUTING_KEY_CONTACC = "conta.cc";

	public void sendMessage(ContaNewDTO conta) {
		rabbitTemplate.convertAndSend(CONTA_EXCHANGE, ROUTING_KEY_CONTACC, conta);
	}
	
	public void sendUpdateMessage(ContaUpdateDTO conta) {
		rabbitTemplate.convertAndSend(CONTA_EXCHANGE, ROUTING_KEY_CONTACC, conta);
	}
}