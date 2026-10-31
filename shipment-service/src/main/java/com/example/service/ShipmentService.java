package com.example.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.dto.ShipmentRequest;
import com.example.event.ShipmentEvent;

import reactor.core.publisher.Mono;

@Service
public class ShipmentService {

    private final ShipmentEventProducer producer;

    public ShipmentService(
            ShipmentEventProducer producer) {

        this.producer = producer;
    }

    public Mono<String> createShipment(
            ShipmentRequest request) {

        String shipmentId =
                "SHP-" + System.currentTimeMillis();

        ShipmentEvent event =
                new ShipmentEvent();

        event.setEventId(
                "EVT-" + System.currentTimeMillis());

        event.setEventType(
                "SHIPMENT_CREATED");

        event.setShipmentId(
                shipmentId);

        event.setCustomerId(
                request.getCustomerId());

        event.setCustomerName(
                request.getCustomerName());

        event.setStatus(
                "CREATED");

        event.setTimestamp(
                LocalDateTime.now().toString());

        return producer.publish(
                event,
                "shipment.created")
                .map(msg ->
                        "Shipment Created : "
                                + shipmentId);
    }

    public Mono<String> updateStatus(
            String shipmentId,
            String status,
            String eventType) {

        ShipmentEvent event =
                new ShipmentEvent();

        event.setEventId(
                "EVT-" + System.currentTimeMillis());

        event.setEventType(
                eventType);

        event.setShipmentId(
                shipmentId);

        event.setStatus(
                status);

        event.setTimestamp(
                LocalDateTime.now().toString());

        String routingKey =
                getRoutingKey(eventType);

        return producer.publish(
                event,
                routingKey)
                .map(msg ->
                        "Shipment Updated Successfully");
    }

    private String getRoutingKey(
            String eventType) {

        return switch (eventType) {

            case "SHIPMENT_CREATED"
                    -> "shipment.created";

            case "SHIPMENT_CANCELLED"
                    -> "shipment.cancelled";

            case "SHIPMENT_DELIVERED"
                    -> "shipment.delivered";

            case "SHIPMENT_PAID"
                    -> "shipment.paid";

            case "SHIPMENT_PACKED"
                    -> "shipment.packed";

            case "SHIPMENT_SHIPPED"
                    -> "shipment.shipped";

            case "SHIPMENT_FAILED"
                    -> "shipment.failed";

            case "DELIVERY_ASSIGNED"
                    -> "delivery.assigned";

            case "DELIVERY_FAILED"
                    -> "delivery.failed";

            default ->
                    throw new RuntimeException(
                            "Unknown Event Type");
        };
    }
}
