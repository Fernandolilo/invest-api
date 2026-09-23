package com.invest.infraestrutura.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageConfig {

	public static final String CONTA_EXCHANGE = "conta.exchange";

	public static final String CONTA_QUEUE_CC = "conta.queue";

	public static final String RENDAFIXA_QUEUE = "rendafixa.queue";

	public static final String ROUTING_KEY_CONTACC = "conta.cc";
	public static final String ROUTING_KEY_RENDAFIXA = "rendafixa.cc";

	@Bean
	public Queue queueCategory() {
		return new Queue(CONTA_QUEUE_CC, true);
	}

	@Bean
	public Queue queueProduto() {
		return new Queue(RENDAFIXA_QUEUE, true);
	}

	@Bean
	public Exchange declareExchange() {
		return ExchangeBuilder.topicExchange(CONTA_EXCHANGE).durable(true).build();
	}

	@Bean
	public Binding bindingCategory(Queue queueCategory, Exchange declareExchange) {
		return BindingBuilder.bind(queueCategory).to((TopicExchange) declareExchange).with(ROUTING_KEY_CONTACC);
	}

	@Bean
	public Binding bindingProduto(Queue queueProduto, Exchange declareExchange) {
		return BindingBuilder.bind(queueProduto).to((TopicExchange) declareExchange).with(ROUTING_KEY_RENDAFIXA);
	}

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

}