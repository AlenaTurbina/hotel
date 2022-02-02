package com.hotel.service.impl;

import com.hotel.model.entity.OrderBooking;
import com.hotel.service.EmailSenderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService {

    private JavaMailSender mailSender;
    private SpringTemplateEngine thymeleafTemplateEngine;

    @Override
    public void sendSimpleEmail (String toEmail, String body, String subject){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(toEmail);
        simpleMailMessage.setText(body);
        simpleMailMessage.setSubject(subject);
        mailSender.send(simpleMailMessage);
        log.info("Send simple mail message" + toEmail);
    }

    @Override
    public void sendHtmlEmail(String toEmail, String subject, String htmlBody) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        mimeMessageHelper.setTo(toEmail);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(htmlBody, true);
        mailSender.send(mimeMessage);
        log.info("Send HTML mail message" + toEmail);
    }

    @Override
    public void sendHtmlEmailInvoice(String toEmail, String subject, OrderBooking orderBooking, String template)
            throws MessagingException {

        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("date", orderBooking.getDate());
        templateModel.put("firstName", orderBooking.getClient().getFirstName());
        templateModel.put("lastName", orderBooking.getClient().getLastName());
        templateModel.put("dateArrival", orderBooking.getDateArrival());
        templateModel.put("dateDeparture", orderBooking.getDateDeparture());
        templateModel.put("roomType", orderBooking.getRoom().getRoomKind().getRoomType().getName());
        templateModel.put("classApartment", orderBooking.getRoom().getRoomKind().getClassApartment().getName());
        templateModel.put("sumTotal", orderBooking.getSumTotal());

        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(templateModel);
        String htmlBody = thymeleafTemplateEngine.process(template, thymeleafContext);

        sendHtmlEmail(toEmail, subject, htmlBody);
    }


}
