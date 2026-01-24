package com.barcraftnz.web;

import com.barcraftnz.domain.Enquiry;
import com.barcraftnz.repo.EnquiryRepository;
import com.barcraftnz.service.EmailService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
public class WebController {

    private final EnquiryRepository enquiryRepository;
    private final EmailService emailService;

    public WebController(EnquiryRepository enquiryRepository, EmailService emailService) {
        this.enquiryRepository = enquiryRepository;
        this.emailService = emailService;
    }

    // HOME
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("enquiry", new Enquiry());
        return "index";
    }

    // SEO SERVICE PAGES (Auckland-focused pages, you operate NZ-wide but these target Auckland search)
    @GetMapping("/mobile-bar-auckland")
    public String mobileBarAuckland(Model model) {
        model.addAttribute("enquiry", new Enquiry());
        return "mobile-bar-auckland";
    }

    @GetMapping("/bartender-for-house-party-auckland")
    public String housePartyBartenderAuckland(Model model) {
        model.addAttribute("enquiry", new Enquiry());
        return "bartender-for-house-party-auckland";
    }

    @GetMapping("/wedding-bar-service-auckland")
    public String weddingBarAuckland(Model model) {
        model.addAttribute("enquiry", new Enquiry());
        return "wedding-bar-service-auckland";
    }

    @GetMapping("/corporate-event-bar-auckland")
    public String corporateBarAuckland(Model model) {
        model.addAttribute("enquiry", new Enquiry());
        return "corporate-event-bar-auckland";
    }

    // SUPPORT PAGES
    @GetMapping("/packages")
    public String packages(Model model) {
        model.addAttribute("enquiry", new Enquiry());
        return "packages";
    }

    @GetMapping("/gallery")
    public String gallery() {
        return "gallery";
    }

    @GetMapping("/success")
    public String success() {
        return "success";
    }
    @GetMapping("/house-party-bartender-auckland")
    public String redirectHousePartyAlias() {
        return "redirect:/bartender-for-house-party-auckland";
    }

    @ModelAttribute("canonicalUrl")
    public String canonicalUrl(HttpServletRequest request) {
        return ServletUriComponentsBuilder
                .fromRequest(request)
                .replaceQuery(null)
                .build()
                .toUriString();
    }

    /**
     * IMPORTANT: keep this working.
     * Your form posts to /enquiry and redirects to /success.
     */
    @PostMapping("/enquiry")
    public String submitEnquiry(@ModelAttribute Enquiry enquiry) {
        enquiryRepository.save(enquiry);

        // Keep your existing EmailService method name.
        // If your method name differs, change only the line below.
        try {
            emailService.sendEnquiryEmail(enquiry);
        } catch (Exception ignored) {
            // Don't break lead capture if email fails
        }

        return "redirect:/success";
    }
}