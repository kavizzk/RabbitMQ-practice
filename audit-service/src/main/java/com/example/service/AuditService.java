package com.example.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.ShipmentAudit;
import com.example.event.ShipmentEvent;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AuditService {

    private final List<ShipmentAudit> auditStore =
            new ArrayList<>();

    public Mono<String> saveAudit(
            ShipmentEvent event) {

        ShipmentAudit audit =
                new ShipmentAudit();

        audit.setEventId(
                event.getEventId());

        audit.setShipmentId(
                event.getShipmentId());

        audit.setEventType(
                event.getEventType());

        audit.setPayload(
                event.toString());

        audit.setProcessedAt(
                LocalDateTime.now());

        auditStore.add(audit);

        return Mono.just(
                "AUDIT SAVED : "
                        + event.getEventType());
    }

    public Flux<ShipmentAudit> getAllAudits() {

        return Flux.fromIterable(
                auditStore);
    }
}

