package com.example.thanh_toan_asm.confignations;

import com.example.thanh_toan_asm.handles.WebhookHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final WebhookHandler webhookHandler;

    public WebSocketConfig(WebhookHandler webhookHandler) {
        this.webhookHandler = webhookHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webhookHandler, "/asmSocket").setAllowedOrigins("vietbm.com");
    }
}
