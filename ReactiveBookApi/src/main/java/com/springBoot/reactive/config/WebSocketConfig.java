package com.springBoot.reactive.config;

import com.springBoot.reactive.notificationHandler.NotificationWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class WebSocketConfig {
//    step-2
    //kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic new-book-topic --from-beginning > bin>windows
//    step-1 is server.properties after creating kafka-logs this is the 1st step otherwise step 1 is kafka-logs- bin\windows\kafka-storage.bat random-uuid
    //kafka-server-start.bat ..\..\config\kraft\kafka-server.properties - inside bin>windows but inside config>kraft> this is one kafka-server.properties should present

    @Bean
    public SimpleUrlHandlerMapping handlerMapping(NotificationWebSocketHandler handler) {
        Map<String, WebSocketHandler> map = new HashMap<>();
        map.put("/ws/notifications", handler);

        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        mapping.setOrder(-1); // Ensure it has high priority
        mapping.setUrlMap(map);
        return mapping;
    }

    @Bean
    public WebSocketHandlerAdapter handlerAdapter() {
        return new WebSocketHandlerAdapter();
    }
}
