package com.prog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/services")
    public String services() {
        return "services"; // Renders services.html
    }

    @GetMapping("/about")
    public String about() {
        return "about"; // Renders about.html
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact"; // Renders contact.html
    }
   
    @GetMapping("/payment")
    public String payment() {
        return "payment"; // Renders pay.html
    }
}