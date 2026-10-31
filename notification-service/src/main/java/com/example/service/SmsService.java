package com.example.service;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class SmsService {

    public Mono<Void> sendSms(
            String customerName) {

        return Mono.fromRunnable(() -> {

            System.out.println(
                    "SMS sent to : "
                    + customerName);

        });
    }
}
