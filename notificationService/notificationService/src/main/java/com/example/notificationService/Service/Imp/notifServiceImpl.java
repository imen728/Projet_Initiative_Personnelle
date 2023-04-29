package com.example.notificationService.Service.Imp;

import com.example.notificationService.Domain.NotifMessage;
import com.example.notificationService.Domain.User;
import com.example.notificationService.Service.notifService;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.twilio.type.PhoneNumber;


@Component
public class notifServiceImpl implements notifService {

    @Value("${twilio.phone.number}")
    private String phoneNumber;

    /**
     * This method sends SMS using the static creator() method
     * Message.creator() has multiple implementations
     * For our scenario, it takes 3 parameters:
     * phoneNumber of the receiver as the first param
     * phoneNumber of the sender as the second param (Twilio number)
     * message (or alertMessage) as the third param
     */
    @Override
    public void sendSMS(User user, NotifMessage notifMessage) {
        Message.creator(new PhoneNumber(user.getPhoneNumber()),
                new PhoneNumber(phoneNumber),
                notifMessage.getMessage()).create();
    }
}