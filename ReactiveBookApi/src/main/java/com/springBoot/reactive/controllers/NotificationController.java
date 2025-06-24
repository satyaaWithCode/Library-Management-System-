package com.springBoot.reactive.controllers;

import com.springBoot.reactive.notificationHandler.NotificationWebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationWebSocketHandler handler;

    @GetMapping("/test-broadcast")
    public Mono<ResponseEntity<String>> test(@RequestParam String msg) {
        return handler.broadcast(msg).thenReturn(ResponseEntity.ok("✅ Sent: " + msg));
    }
}
