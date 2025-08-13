package com.invest.infraestrutura.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageConfig {

    @Value("${invest.rabbitmq.exchange}")
    private String exchange;

    @Value("${invest.rabbitmq.queue}")
    private String queueName;

    @Value("${invest.rabbitmq.routingKey:conta.routing.key}")
    private String routingKey;

    @Bean
    public Exchange declareExchange() {
        return ExchangeBuilder.directExchange(exchange).durable(true).build();
    }

    @Bean
    public Queue declareQueue() {
        return new Queue(queueName, true);
    }

    @Bean
    public Binding declareBinding(Queue queue, Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey).noargs();
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
