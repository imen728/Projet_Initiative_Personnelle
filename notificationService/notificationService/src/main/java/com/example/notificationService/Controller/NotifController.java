package com.example.notificationService.Controller;


import com.example.notificationService.Domain.NotifMessage;
import com.example.notificationService.Domain.User;
import com.example.notificationService.Service.notifService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/v1")
public class NotifController {

    @Autowired
    notifService notifService;

    @PostMapping("/sms")
    public ResponseEntity<String> sendAlertMessage(@RequestBody NotifMessage notifMessage) {

        User user = new User("Imen", "Dridi", "+21620137944");
        notifService.sendSMS(user, notifMessage);
        return new ResponseEntity<>("Notif Message Sent Successfully!", HttpStatus.OK);

    }
}