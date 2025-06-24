package com.springBoot.reactive.service;

import reactor.core.publisher.Mono;

public interface KafkaProducerService {

    Mono<Void> sendNewBookNotification(String message);
}
