package com.springBoot.reactive.notificationHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@Slf4j
public class NotificationWebSocketHandler implements WebSocketHandler {

    private final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

    //here websocket will connected to kafka broker
    @Override
    public Mono<Void> handle(WebSocketSession session) {
        sessions.add(session);
        log.info("🔌 WebSocket connected: {}", session.getId());

        return session.receive()
                .doFinally(signal -> {
                    sessions.remove(session);
                    log.info("❌ WebSocket disconnected: {}", session.getId());
                })
                .then();
    }

    //message will broadcast here
    //notiication message will going from here to client
    public Mono<Void> broadcast(String message) {
        return Mono.when(
                sessions.stream()
                        .map(session -> session.send(Mono.just(session.textMessage(message))))
                        .toList()
        );
    }
}




//
//package com.springBoot.reactive.notificationHandler;
//
//import com.springBoot.reactive.securities.JwtHelper;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import org.springframework.web.reactive.socket.WebSocketHandler;
//import org.springframework.web.reactive.socket.WebSocketSession;
//import reactor.core.publisher.Mono;
//
//import java.util.Set;
//import java.util.concurrent.CopyOnWriteArraySet;
//
//@Component
//@Slf4j
//@RequiredArgsConstructor
//public class NotificationWebSocketHandler implements WebSocketHandler {
//
//    private final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();
//    private final JwtHelper jwtHelper;
//
//    @Override
//    public Mono<Void> handle(WebSocketSession session) {
//        String token = getTokenFromQuery(session);
//
//        if (token == null || !jwtHelper.validateToken(token)) {
//            log.warn("❌ WebSocket rejected: Invalid or missing token");
//            return session.close();
//        }
//
//        sessions.add(session);
//        log.info("🔐 WebSocket connected: {}", session.getId());
//
//        return session.receive()
//                .doFinally(signalType -> {
//                    sessions.remove(session);
//                    log.info("❌ WebSocket disconnected: {}", session.getId());
//                })
//                .then();
//    }
//
//    public Mono<Void> broadcast(String message) {
//        return Mono.when(
//                sessions.stream()
//                        .map(session -> session.send(Mono.just(session.textMessage(message))))
//                        .toList()
//        );
//    }
//
//    private String getTokenFromQuery(WebSocketSession session) {
//        String query = session.getHandshakeInfo().getUri().getQuery();
//        if (query != null && query.startsWith("token=")) {
//            return query.substring("token=".length());
//        }
//        return null;
//    }
//}
