package com.springBoot.reactive.service.impl;


import com.springBoot.reactive.kafkaTopic.AppConstant;
import com.springBoot.reactive.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class KafkaProducerServiceImpl implements KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate; //it will send the location & message to user

    @Override
    public Mono<Void> sendNewBookNotification(String message) {
        // Directly wrap the CompletableFuture returned by send(...) into a Mono
        return Mono.fromFuture(kafkaTemplate.send(AppConstant.NEW_BOOK_TOPIC, message))
                .then(); // Convert to Mono<Void>
    }
}
