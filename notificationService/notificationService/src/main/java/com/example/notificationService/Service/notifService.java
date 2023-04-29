package com.example.notificationService.Service;

import com.example.notificationService.Domain.NotifMessage;
import com.example.notificationService.Domain.User;
import org.springframework.stereotype.Service;

@Service
public interface notifService {
    void sendSMS(User user, NotifMessage message);
}