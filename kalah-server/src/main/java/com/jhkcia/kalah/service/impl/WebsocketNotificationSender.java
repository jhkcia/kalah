package com.jhkcia.kalah.service.impl;

import com.jhkcia.kalah.service.BoardNotificationSender;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

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
