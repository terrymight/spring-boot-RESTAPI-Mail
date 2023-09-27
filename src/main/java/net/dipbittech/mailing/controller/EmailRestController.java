package net.dipbittech.mailing.controller;

import net.dipbittech.mailing.entity.NotificationRequest;
import net.dipbittech.mailing.entity.SendHtmlRequest;
import net.dipbittech.mailing.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class EmailRestController {

    @Autowired
    private  EmailService emailService;
    @PostMapping("sendNotification")
    public String sendNotification (@RequestBody NotificationRequest notificationRequest){
       emailService.simpleSend(notificationRequest);
        return "Message Queued";
    }

    @PostMapping("sendHTMLEmail")
    public  String sendHTMLEmail(@RequestBody SendHtmlRequest sendHtmlRequest){

        emailService.sendHtml(sendHtmlRequest);
        return "Send HTML Email Queue";
    }
}
