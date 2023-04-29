package com.example.notificationService.Service;

import com.example.notificationService.Domain.MailDetail;

public interface MailService {
    String sendMail(MailDetail mailDetail);
    String sendMailWithAttachment(MailDetail mailDetail);
}
