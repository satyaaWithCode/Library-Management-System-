package com.springBoot.reactive.service.impl;

import com.springBoot.reactive.kafkaTopic.AppConstant;
import com.springBoot.reactive.notificationHandler.NotificationWebSocketHandler;
import com.springBoot.reactive.service.KafkaConsumerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerServiceImpl implements KafkaConsumerService {

    private final NotificationWebSocketHandler webSocketHandler;

    @KafkaListener(topics = AppConstant.NEW_BOOK_TOPIC, groupId = AppConstant.KAFKA_GROUP_ID)
    public void listen(String message) {
        log.info("📨 Kafka message received: {}", message);
        consumeMessage(message).subscribe();
    }

    @Override
    public Mono<Void> consumeMessage(String message) {
        return webSocketHandler.broadcast(message);
    }
}
