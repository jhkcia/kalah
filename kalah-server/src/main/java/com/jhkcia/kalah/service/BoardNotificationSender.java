package com.jhkcia.kalah.service;

public interface BoardNotificationSender {
    void notifyUpdate(long boardId, String payload);
}
