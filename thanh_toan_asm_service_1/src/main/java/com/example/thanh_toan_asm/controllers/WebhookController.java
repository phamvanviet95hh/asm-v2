package com.example.thanh_toan_asm.controllers;

import com.example.thanh_toan_asm.handles.WebhookHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/asm/v1")
public class WebhookController {
    @Autowired
    WebhookHandler webhookHandler;

    @PostMapping("/receive")
    public ResponseEntity<String> handleWebhook(@RequestBody Map<String, Object> payload) {
        System.out.println("Webhook received with payload: " + payload);
        try {
            String data = payload.toString();
            // Gửi dữ liệu qua WebSocket
            System.out.println("data :" +data);
            webhookHandler.sendMessage(data);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok("Error sending messagey");
        }
        return ResponseEntity.ok("Webhook received successfully");
    }
}
