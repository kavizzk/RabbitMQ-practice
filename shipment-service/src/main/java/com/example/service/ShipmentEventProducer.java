package com.example.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.example.constants.RabbitMQConstants;
import com.example.event.ShipmentEvent;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class ShipmentEventProducer {

    private final RabbitTemplate rabbitTemplate;

    public ShipmentEventProducer(
            RabbitTemplate rabbitTemplate) {

        this.rabbitTemplate = rabbitTemplate;
    }

    public Mono<String> publish(
            ShipmentEvent event,
            String routingKey) {

        return Mono.fromRunnable(() -> {

            System.out.println(
                    "PUBLISHING -> "
                            + event.getEventType()
                            + " | "
                            + routingKey);

            rabbitTemplate.convertAndSend(
                    RabbitMQConstants.EXCHANGE,
                    routingKey,
                    event);

        }).subscribeOn(
                Schedulers.boundedElastic())
          .thenReturn(
                  "Event Published Successfully");
    }
}