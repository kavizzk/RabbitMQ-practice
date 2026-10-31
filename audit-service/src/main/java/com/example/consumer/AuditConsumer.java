package com.example.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.example.event.ShipmentEvent;
import com.example.service.AuditService;

@Component
public class AuditConsumer {

    private final AuditService auditService;

    public AuditConsumer(
            AuditService auditService) {

        this.auditService = auditService;
    }

    @RabbitListener(
            queues = "shipment.audit.queue")
    public void consume(
            ShipmentEvent event) {

        auditService
                .saveAudit(event)
                .subscribe(result -> {

                    System.out.println(
                            result);

                });
    }
}