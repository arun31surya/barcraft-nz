package com.barcraftnz.web;

import com.barcraftnz.domain.Enquiry;
import com.barcraftnz.repo.EnquiryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class WebController {

    private final EnquiryRepository enquiryRepository;

    public WebController(EnquiryRepository enquiryRepository) {
        this.enquiryRepository = enquiryRepository;
    }

    // Home page – just return the template, no model needed
    @GetMapping("/")
    public String showHome() {
        return "index";
    }

    // Handle form submit
    @PostMapping("/enquiry")
    public String handleEnquiry(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false, name = "date") String eventDate,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String guests,
            @RequestParam(required = false) String details,
            RedirectAttributes redirectAttributes
    ) {

        Enquiry enquiry = new Enquiry();
        enquiry.setName(name);
        enquiry.setEmail(email);
        enquiry.setPhone(phone);
        enquiry.setDate(eventDate);
        enquiry.setLocation(location);
        enquiry.setGuests(guests);
        enquiry.setDetails(details);

        enquiryRepository.save(enquiry);

        // flash attribute survives the redirect and shows the thank-you message
        redirectAttributes.addFlashAttribute("sent", true);

        return "redirect:/";
    }
}
