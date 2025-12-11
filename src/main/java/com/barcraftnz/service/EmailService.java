package com.barcraftnz.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.barcraftnz.domain.Enquiry;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    // will be info@barcraft.co.nz, from application.properties/env
    @Value("${spring.mail.username}")
    private String businessEmail;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEnquiryEmail(Enquiry enquiry) {
        SimpleMailMessage message = new SimpleMailMessage();

        // email goes TO your business inbox
        message.setTo(businessEmail);
        // from your business address as well
        message.setFrom(businessEmail);

        message.setSubject("New BarCraft NZ enquiry from " + enquiry.getName());

        String body = String.format(
                "Name: %s%n" +
                        "Email: %s%n" +
                        "Phone: %s%n" +
                        "Guests: %s%n" +
                        "Event Date: %s%n" +
                        "Location: %s%n%n" +
                        "Message:%n%s",
                enquiry.getName(),
                enquiry.getEmail(),
                enquiry.getPhone(),
                enquiry.getGuests(),
                enquiry.getDate(),
                enquiry.getLocation(),
                enquiry.getDetails()
        );

        message.setText(body);
        try {
            mailSender.send(message);
        } catch (MailException ex) {
            System.err.println("Failed to send enquiry email:"+ex.getMessage());
        }

    }
}