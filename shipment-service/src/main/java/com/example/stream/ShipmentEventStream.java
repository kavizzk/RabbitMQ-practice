package com.example.stream;

import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Component
public class ShipmentEventStream {

    private final Sinks.Many<String> sink =
            Sinks.many()
                 .multicast()
                 .onBackpressureBuffer();

    public void publish(String message) {

        sink.tryEmitNext(message);
    }

    public Flux<String> stream() {

        return sink.asFlux();
    }
}
