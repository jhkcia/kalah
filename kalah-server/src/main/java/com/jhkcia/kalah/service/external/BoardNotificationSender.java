package com.jhkcia.kalah.service.external;

public interface BoardNotificationSender {
    void notifyUpdate(long boardId, String payload);
}
