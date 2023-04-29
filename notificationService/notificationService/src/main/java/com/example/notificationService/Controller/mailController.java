package com.example.notificationService.Controller;
import com.example.notificationService.Domain.MailDetail;
import com.example.notificationService.Service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class mailController {
    @Autowired
    private MailService mailService;

    //Sending email
    @PostMapping("/send-mail")
    public String sendMail(@RequestBody MailDetail mailDetail) {
        return mailService.sendMail(mailDetail);
    }
    //Sending email with attachment
    @PostMapping("/send-mail-attachment")
    public String sendMailWithAttachment(@RequestBody MailDetail mailDetail)
    {
        return mailService.sendMailWithAttachment(mailDetail);
    }
}