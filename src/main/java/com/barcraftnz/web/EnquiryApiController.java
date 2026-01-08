package com.barcraftnz.web;

import com.barcraftnz.domain.Enquiry;
import com.barcraftnz.repo.EnquiryRepository;
import com.barcraftnz.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class EnquiryApiController {

    private final EnquiryRepository enquiryRepository;
    private final EmailService emailService;

    public EnquiryApiController(EnquiryRepository enquiryRepository,
                                EmailService emailService) {
        this.enquiryRepository = enquiryRepository;
        this.emailService = emailService;
    }

    @PostMapping("/enquiry")
    public ResponseEntity<?> submit(@RequestBody Enquiry enquiry) {

        enquiryRepository.save(enquiry);

        try {
            emailService.sendEnquiryEmail(enquiry);
        } catch (Exception ignored) {
        }

        return ResponseEntity.ok(
                Map.of("message", "Enquiry received. We’ll reply shortly.")
        );
    }
}