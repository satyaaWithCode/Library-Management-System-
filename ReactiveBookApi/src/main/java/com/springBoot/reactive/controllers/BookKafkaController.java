package com.springBoot.reactive.controllers;

import com.springBoot.reactive.DTO.ApiResponse;
import com.springBoot.reactive.entity.Book;
import com.springBoot.reactive.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("kafka/books")
@RequiredArgsConstructor
public class BookKafkaController {

    private final KafkaProducerService kafkaProducerService;

    // Kafka uses its own TCP protocol, not HTTP or WebSocket.
    //UI direct  can not communicate with  Kafka
    //imp- when admin will addBook as a producer that will comes to topic & websocket get that topic data & share all connected users

    @PostMapping("/notify")
    public Mono<ResponseEntity<ApiResponse<String>>> sendNotification(@RequestBody Book book) {
        String message = "📚 New book added: " + book.getName() + " by " + book.getAuthor();
        return kafkaProducerService.sendNewBookNotification(message)
                .thenReturn(
                        ResponseEntity.ok(new ApiResponse<>("success", "Kafka notification sent successfully", message))
                );
    }

}
