package com.barcraftnz.service;


import com.barcraftnz.domain.Enquiry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("${mail.business.to}")
    private String businessEmail;

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEnquiryEmail(Enquiry enquiry) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(businessEmail);         // goes to info@barcraft.co.nz
            message.setFrom(businessEmail);       // from info@barcraft.co.nz
            message.setSubject("New BarCraft NZ enquiry from: " + enquiry.getName());

            String body = String.format("""
                    Name: %s
                    Email: %s
                    Phone: %s
                    Guests: %s
                    Event date: %s
                    Location: %s

                    Message:
                    %s
                    """,
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
            System.out.println(">>> Enquiry email SENT successfully");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println(">>> FAILED to send enquiry email: " + ex.getMessage());
        }
    }
}