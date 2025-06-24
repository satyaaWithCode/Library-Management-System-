package com.springBoot.reactive.service;

import reactor.core.publisher.Mono;

public interface KafkaConsumerService {
    Mono<Void> consumeMessage(String message);
}
