package com.example.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.example.event.ShipmentEvent;
import com.example.service.EmailService;
import com.example.service.SmsService;

import reactor.core.publisher.Mono;

@Component
public class NotificationConsumer {

    private final EmailService emailService;

    private final SmsService smsService;

    public NotificationConsumer(
            EmailService emailService,
            SmsService smsService) {

        this.emailService = emailService;
        this.smsService = smsService;
    }

    @RabbitListener(
            queues =
            "shipment.notification.queue")
    public void consume(
            ShipmentEvent event) {

        System.out.println();
        System.out.println(
                "================================");

        System.out.println(
                "NOTIFICATION SERVICE");

        System.out.println(
                "================================");

        System.out.println(
                "Shipment ID : "
                + event.getShipmentId());

        System.out.println(
                "Event Type  : "
                + event.getEventType());

        Mono.when(

                emailService.sendEmail(
                        event.getCustomerName()),

                smsService.sendSms(
                        event.getCustomerName())

        ).doOnSuccess(v -> {

            System.out.println(
                    "Reactive Notifications Completed");

            System.out.println(
                    "================================");

        }).subscribe();
    }
}
