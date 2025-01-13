package com.main.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.main.entities.Admin;
import com.main.entities.Users;
import com.main.repository.AdminRepo;
import com.main.repository.UserRepo;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class HomeController {

  @Autowired
  UserRepo userRepo;

  @Autowired
  AdminRepo adminRepo;

  @GetMapping("/")
  public String openIndexPage() {
    return "index";
  }

  @GetMapping("/user")
  public String openUserPage(@ModelAttribute("user") Users user, Model model) {
    model.addAttribute("user", user);

    return "user_register";
  }

  @PostMapping("/submit_user")
  public String submitUser(@ModelAttribute Users user, Model model) {
    try {

      Users users = userRepo.save(user);
      if (users != null) {
        model.addAttribute("succ", "User Register Successfully");
      } else {
        model.addAttribute("err", "User Register Failed");
      }

    } catch (Exception e) {
      e.printStackTrace();
      model.addAttribute("err", "An error occurred during registration.");
    }

    return "redirect:/user";
  }

  @GetMapping("/login_user")
  public String loginUser() {
    return "login_user";
  }

  @GetMapping("/admin")
  public String adminPage(@ModelAttribute("admin") Admin admin, Model model) {
    model.addAttribute("admin", admin);
    return "admin_login";
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

    return "redirect:/admin";
  }

  @GetMapping("/logout")
  public String logout(HttpSession session) {
    session.invalidate(); // Clear the session
    return "redirect:/"; // Redirect to the login page
  }

}
