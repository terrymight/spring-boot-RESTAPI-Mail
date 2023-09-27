package net.dipbittech.mailing.service;

import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dipbittech.mailing.entity.NotificationRequest;
import net.dipbittech.mailing.entity.SendHtmlRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

@Async
    public void simpleSend(NotificationRequest request){
        log.info("simpleSend. subject: {} :email: {} :message: {} ", request.getSubject(),request.getEmail(),request.getMessage());

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

            helper.setFrom("info@garbcs.com","Programmer");
            helper.setTo(request.getEmail());
            helper.setSubject(request.getSubject());
            helper.setText(request.getMessage(),true);

            javaMailSender.send(mimeMessage);
            log.info("simpleSend: Email Queued");

        }catch (Exception ex){
            ex.getStackTrace();
        }
    }

    @Async
    public void sendHtml(SendHtmlRequest sendHtmlRequest) {

    log.info("SendHTML. subject: {} :email: {} : message: {} "
            ,sendHtmlRequest.getSubject(),sendHtmlRequest.getEmail(),sendHtmlRequest.getName());

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

            helper.setFrom("info@garbc.com", "Garbc Ltd");
            helper.setTo(sendHtmlRequest.getEmail());
            helper.setSubject(sendHtmlRequest.getSubject());

            //thymeleaf context
            Context context = new Context();

            // Properties to show up in Template after stored in Context
            Map<String, Object> properties = new HashMap<String, Object>();

            properties.put("name",sendHtmlRequest.getName());
            properties.put("offerings", sendHtmlRequest.getOfferings());

            context.setVariables(properties);

            String html = templateEngine.process("email/" + sendHtmlRequest.getTemplateName(), context);

            helper.setText(html,true);

            log.info(html);

            javaMailSender.send(mimeMessage);
            log.info("HtmlSend: Email Queued");

        }catch (Exception e){
            e.getStackTrace();
        }
    }
}
