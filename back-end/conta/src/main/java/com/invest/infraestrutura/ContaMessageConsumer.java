package com.invest.infraestrutura;

import java.util.Optional;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.invest.dto.ContaUpdateDTO;
import com.invest.entity.Conta;
import com.invest.infraestrutura.config.MessageConfig;
import com.invest.reposiotries.ContaRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ContaMessageConsumer {

	private final ContaRepository repository;
	
	@RabbitListener(queues = MessageConfig.RENDAFIXA_QUEUE)
	public void receiveConta(@Payload ContaUpdateDTO conta) {
	    
	    repository.findById(conta.getId()).ifPresentOrElse(cc -> {
	        // Atualiza a conta existente
	        Conta updateConta = Conta.builder()
	                .id(conta.getId())
	                .numero(conta.getNumero())
	                .agencia(conta.getAgencia())
	                .saldo(conta.getSaldo())
	                .banco(conta.getBanco())  // corrigido para pegar o banco certo
	                .tipo(conta.getTipo())
	                .client(cc.getClient())
	                .cpfOuCnpj(conta.getCpf())
	                .build();
	        repository.save(updateConta);
	        System.out.println("Conta atualizada: " + cc.getId());
	    }, () -> {
	        System.out.println("Conta não encontrada para atualização: " + conta.getId());
	        // opcional: você pode criar a conta ou enviar para dead-letter
	    });
	}

}