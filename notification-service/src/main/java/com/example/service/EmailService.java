package com.example.service;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class EmailService {

    public Mono<Void> sendEmail(
            String customerName) {

        return Mono.fromRunnable(() -> {

            System.out.println(
                    "Email sent to : "
                    + customerName);

        });
    }
}
