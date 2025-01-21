package com.main.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.main.entities.Admin;
import com.main.repository.AdminRepo;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    @Autowired
    AdminRepo adminRepo;

    @GetMapping("/admin")
    public String adminPage(@ModelAttribute("admin") Admin admin, Model model) {
        model.addAttribute("admin", admin);
        return "admin/admin_login";
    }

    @PostMapping("/submit_admin_login")
    public String adminLoginPage(@ModelAttribute Admin admin, Model model, HttpSession session) {
        try {
            Admin adminEmail = adminRepo.findByEmail(admin.getEmail());
            if (adminEmail != null && adminEmail.getPassword().equals("admin")) {
                System.out.println("Welcome");
                session.setAttribute("role", "admin");
                session.setAttribute("msg", "Welcome Admin");
                return "redirect:/";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/";
    }

}
