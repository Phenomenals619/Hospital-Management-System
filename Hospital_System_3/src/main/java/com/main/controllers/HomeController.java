package com.main.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.main.repository.UserRepo;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

  @Autowired
  UserRepo userRepo;

  @GetMapping("/")
  public String openIndexPage() {
    return "index";
  }

  @GetMapping("/logout")
  public String logout(HttpSession session) {
    session.invalidate(); // Clear the session
    return "redirect:/"; // Redirect to the login page
  }

}
