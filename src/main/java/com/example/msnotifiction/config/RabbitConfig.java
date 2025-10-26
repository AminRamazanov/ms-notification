package com.example.msnotifiction.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String EXCHANGE = "NOTIFICATION_EXC";
    public static final String NOTIFICATION_DLX = "NOTIFICATION_DLX";

    public static final String REGISTRATION_QUEUE = "notification.registration.queue";
    public static final String RECOVERY_PASSWORD_QUEUE = "notification.recovery.password.queue";
    public static final String ORDER_RESULT_QUEUE = "notification.order.result.queue";
    public static final String ORDER_READY_QUEUE = "notification.order.ready.queue";
    public static final String ORDER_COMPLETED_QUEUE = "notification.order.completed.queue";

    public static final String REGISTRATION_ROUTING_KEY = "notification.registration";
    public static final String RECOVERY_PASSWORD_ROUTING_KEY = "notification.recovery.password";
    public static final String ORDER_RESULT_ROUTING_KEY = "notification.order.result";
    public static final String ORDER_READY_ROUTING_KEY = "notification.order.ready";
    public static final String ORDER_COMPLETED_ROUTING_KEY = "notification.order.completed";

    public static final String REGISTRATION_DLQ = "notification.registration.dlq";
    public static final String RECOVERY_PASSWORD_DLQ = "notification.recovery.password.dlq";
    public static final String ORDER_RESULT_DLQ = "notification.order.result.dlq";
    public static final String ORDER_READY_DLQ = "notification.order.ready.dlq";
    public static final String ORDER_COMPLETED_DLQ = "notification.order.completed.dlq";

    public static final String REGISTRATION_DLQ_ROUTING_KEY = "notification.registration.dlq.key";
    public static final String RECOVERY_PASSWORD_DLQ_ROUTING_KEY = "notification.recovery.password.dlq.key";
    public static final String ORDER_RESULT_DLQ_ROUTING_KEY = "notification.order.result.dlq.key";
    public static final String ORDER_READY_DLQ_ROUTING_KEY = "notification.order.ready.dlq.key";
    public static final String ORDER_COMPLETED_DLQ_ROUTING_KEY = "notification.order.completed.dlq.key";


    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public TopicExchange dlx() {
        return new TopicExchange(NOTIFICATION_DLX);
    }

    @Bean
    public Queue registrationQueue() {
        return QueueBuilder.durable(REGISTRATION_QUEUE)
                .withArgument("x-dead-letter-exchange", NOTIFICATION_DLX)
                .withArgument("x-dead-letter-routing-key", REGISTRATION_DLQ_ROUTING_KEY)
                .build();
    }

    @Bean
    public Queue recoveryPasswordQueue() {
        return QueueBuilder.durable(RECOVERY_PASSWORD_QUEUE)
                .withArgument("x-dead-letter-exchange", NOTIFICATION_DLX)
                .withArgument("x-dead-letter-routing-key", RECOVERY_PASSWORD_DLQ_ROUTING_KEY)
                .build();
    }

    @Bean
    public Queue orderResultQueue() {
        return QueueBuilder.durable(ORDER_RESULT_QUEUE)
                .withArgument("x-dead-letter-exchange", NOTIFICATION_DLX)
                .withArgument("x-dead-letter-routing-key", ORDER_RESULT_DLQ_ROUTING_KEY)
                .build();
    }

    @Bean
    public Queue orderCompletedQueue() {
        return QueueBuilder.durable(ORDER_COMPLETED_QUEUE)
                .withArgument("x-dead-letter-exchange", NOTIFICATION_DLX)
                .withArgument("x-dead-letter-routing-key", ORDER_COMPLETED_DLQ_ROUTING_KEY)
                .build();
    }

    @Bean
    public Queue orderReadyQueue() {
        return QueueBuilder.durable(ORDER_READY_QUEUE)
                .withArgument("x-dead-letter-exchange", NOTIFICATION_DLX)
                .withArgument("x-dead-letter-routing-key", ORDER_READY_DLQ_ROUTING_KEY)
                .build();
    }

    @Bean
    public Queue registrationNotifyDlq(){
        return QueueBuilder.durable(REGISTRATION_DLQ).build();
    }

    @Bean
    public Queue recoveryNotifyDlq(){
        return QueueBuilder.durable(RECOVERY_PASSWORD_DLQ).build();
    }

    @Bean
    public Queue orderResultNotifyDlq(){
        return QueueBuilder.durable(ORDER_RESULT_DLQ).build();
    }

    @Bean
    public Queue orderReadyNotifyDlq(){
        return QueueBuilder.durable(ORDER_READY_DLQ).build();
    }

    @Bean
    public Queue orderCompletedNotifyDlq(){
        return QueueBuilder.durable(ORDER_COMPLETED_DLQ).build();
    }

    @Bean
    public Binding bindingRegistrationNotifyQueue() {
        return BindingBuilder.bind(registrationQueue()).to(exchange()).with(REGISTRATION_ROUTING_KEY);
    }

    @Bean
    public Binding bindingRecoveryPasswordNotifyQueue() {
        return BindingBuilder.bind(recoveryPasswordQueue()).to(exchange()).with(RECOVERY_PASSWORD_ROUTING_KEY);
    }

    @Bean
    public Binding bindingOrderResultQueue() {
        return BindingBuilder.bind(orderResultQueue()).to(exchange()).with(ORDER_RESULT_ROUTING_KEY);
    }

    @Bean
    public Binding bindingOrderReadyQueue() {
        return BindingBuilder.bind(orderReadyQueue()).to(exchange()).with(ORDER_READY_ROUTING_KEY);
    }

    @Bean
    public Binding bindingOrderCompletedQueue() {
        return BindingBuilder.bind(orderCompletedQueue()).to(exchange()).with(ORDER_COMPLETED_ROUTING_KEY);
    }

    @Bean
    public Binding bindingRegistrationNotifyDlq() {
        return BindingBuilder.bind(registrationNotifyDlq()).to(dlx()).with(REGISTRATION_DLQ_ROUTING_KEY);
    }

    @Bean
    public Binding bindingRecoveryPasswordNotifyDlq() {
        return BindingBuilder.bind(recoveryNotifyDlq()).to(dlx()).with(RECOVERY_PASSWORD_DLQ_ROUTING_KEY);
    }

    @Bean
    public Binding bindingOrderResultNotifyDlq() {
        return BindingBuilder.bind(orderResultNotifyDlq()).to(dlx()).with(ORDER_RESULT_DLQ_ROUTING_KEY);
    }

    @Bean
    public Binding bindingOrderReadyNotifyDlq() {
        return BindingBuilder.bind(orderReadyNotifyDlq()).to(dlx()).with(ORDER_READY_DLQ_ROUTING_KEY);
    }

    @Bean
    public Binding bindingOrderCompletedNotifyDlq() {
        return BindingBuilder.bind(orderCompletedNotifyDlq()).to(dlx()).with(ORDER_COMPLETED_DLQ_ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jsonMessageConverter());
        return factory;
    }
}
