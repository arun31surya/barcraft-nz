package com.barcraftnz.web;

import com.barcraftnz.domain.Enquiry;
import com.barcraftnz.repo.EnquiryRepository;
import com.barcraftnz.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class WebController {

    private final EnquiryRepository enquiryRepository;
    private final EmailService emailService;

    public WebController(EnquiryRepository enquiryRepository, EmailService emailService) {
        this.enquiryRepository = enquiryRepository;
        this.emailService = emailService;
    }

    @GetMapping("/")
    public String showHome(Model model) {
        model.addAttribute("enquiry", new Enquiry());
        return "index";
    }

    @PostMapping("/enquiry")
    public String submitEnquiry(@Valid @ModelAttribute Enquiry enquiry,
                                BindingResult result) {
        if (result.hasErrors()) {
            return "index";
        }

        // Save in DB (H2) if you want
        enquiryRepository.save(enquiry);

        // Send email to info@barcraft.co.nz
        emailService.sendEnquiryEmail(enquiry);

        return "redirect:/success";
    }

    @GetMapping("/success")
    public String showSuccess() {
        return "success";   // create a simple success.html page
    }

}