package com.example.controller;

import org.springframework.web.bind.annotation.*;

import com.example.dto.ShipmentRequest;
import com.example.service.ShipmentService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/shipments")
public class ShipmentController {

    private final ShipmentService service;

    public ShipmentController(
            ShipmentService service) {

        this.service = service;
    }

    @PostMapping
    public Mono<String> createShipment(
            @RequestBody ShipmentRequest request) {

        return service.createShipment(request);
    }

    @PutMapping("/{shipmentId}/cancel")
    public Mono<String> cancelShipment(
            @PathVariable String shipmentId) {

        return service.updateStatus(
                shipmentId,
                "CANCELLED",
                "SHIPMENT_CANCELLED");
    }

    @PutMapping("/{shipmentId}/deliver")
    public Mono<String> deliverShipment(
            @PathVariable String shipmentId) {

        return service.updateStatus(
                shipmentId,
                "DELIVERED",
                "SHIPMENT_DELIVERED");
    }

    @PutMapping("/{shipmentId}/pay")
    public Mono<String> payShipment(
            @PathVariable String shipmentId) {

        return service.updateStatus(
                shipmentId,
                "PAID",
                "SHIPMENT_PAID");
    }

    @PutMapping("/{shipmentId}/pack")
    public Mono<String> packShipment(
            @PathVariable String shipmentId) {

        return service.updateStatus(
                shipmentId,
                "PACKED",
                "SHIPMENT_PACKED");
    }

    @PutMapping("/{shipmentId}/ship")
    public Mono<String> shipShipment(
            @PathVariable String shipmentId) {

        return service.updateStatus(
                shipmentId,
                "SHIPPED",
                "SHIPMENT_SHIPPED");
    }

    @PutMapping("/{shipmentId}/fail")
    public Mono<String> failShipment(
            @PathVariable String shipmentId) {

        return service.updateStatus(
                shipmentId,
                "FAILED",
                "SHIPMENT_FAILED");
    }

    @PutMapping("/{shipmentId}/assign-delivery")
    public Mono<String> assignDelivery(
            @PathVariable String shipmentId) {

        return service.updateStatus(
                shipmentId,
                "DELIVERY_ASSIGNED",
                "DELIVERY_ASSIGNED");
    }

    @PutMapping("/{shipmentId}/delivery-failed")
    public Mono<String> deliveryFailed(
            @PathVariable String shipmentId) {

        return service.updateStatus(
                shipmentId,
                "DELIVERY_FAILED",
                "DELIVERY_FAILED");
    }
}