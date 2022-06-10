package com.jhkcia.kalah.service.infrastructure;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.jhkcia.kalah.service.external.BoardNotificationSender;

@Component
public class WebsocketNotificationSender implements BoardNotificationSender {
    private final SimpMessagingTemplate simpMessagingTemplate;


    public WebsocketNotificationSender(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Override
    public void notifyUpdate(long boardId, String payload) {
        simpMessagingTemplate.convertAndSend(String.format("/topic/boards/%d/get", boardId), payload);
    }
}
