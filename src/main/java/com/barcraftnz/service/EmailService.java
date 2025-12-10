package com.barcraftnz.service;

import com.barcraftnz.domain.Enquiry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    // This will be info@barcraft.co.nz (from your env var on Render)
    @Value("${MAIL_USERNAME}")
    private String businessEmail;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEnquiryEmail(Enquiry enquiry) {
        SimpleMailMessage message = new SimpleMailMessage();

        // Gmail/Workspace usually requires From to be the authenticated account
        message.setFrom(businessEmail);
        message.setTo(businessEmail);              // you receive the enquiry here
        message.setReplyTo(enquiry.getEmail());    // so you can reply directly to the customer

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

        mailSender.send(message);
    }
}