package com.barcraftnz.gear;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GearController {

    private final AffiliateProperties affiliateProperties;

    public GearController(AffiliateProperties affiliateProperties) {
        this.affiliateProperties = affiliateProperties;
    }

    @GetMapping("/gear")
    public String gearIndex(Model model) {
        model.addAttribute("title", "Gear Guide | BarCraft NZ");
        model.addAttribute("description", "Affiliate gear guides for home bars and house parties in New Zealand.");
        return "gear/gear-index";
    }

    @GetMapping("/gear/cocktail-shaker-sets-nz")
    public String cocktailShakerSetsNz(Model model) {
        model.addAttribute("title", "Best Cocktail Shaker Sets in NZ | BarCraft NZ");
        model.addAttribute("description", "A practical guide to choosing cocktail shaker sets in New Zealand.");
        model.addAttribute("links", affiliateProperties.getLinks());
        return "gear/cocktail-shaker-sets-nz";
    }

    @GetMapping("/gear/portable-bar-tables-nz")
    public String portableBarTablesNz(Model model) {
        model.addAttribute("title", "Best Portable Bar Tables in NZ | BarCraft NZ");
        model.addAttribute("description", "A practical guide to portable bar tables for parties in New Zealand.");
        model.addAttribute("links", affiliateProperties.getLinks());
        return "gear/portable-bar-tables-nz";
    }

    @GetMapping("/affiliate-disclosure")
    public String affiliateDisclosure(Model model) {
        model.addAttribute("title", "Affiliate Disclosure | BarCraft NZ");
        model.addAttribute("description", "Affiliate disclosure for BarCraft NZ gear content.");
        return "legal/affiliate-disclosure";
    }
}