package com.syp.invest.infraestrutura;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.syp.invest.entity.Conta;
import com.syp.invest.infraestrutura.config.MessageConfig;
import com.syp.invest.repositories.ContaRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ContaMessageConsumer {

	private final ContaRepository repository;
	@RabbitListener(queues = MessageConfig.CONTA_QUEUE_CC)
	
	
	public void receiveConta(@Payload Conta conta) {
	    repository.save(conta);
	}

}