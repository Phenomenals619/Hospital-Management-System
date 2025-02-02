package com.main.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.main.entities.Appointment;
import com.main.entities.Users;
import com.main.repository.AppRepo;
import com.main.repository.UserRepo;

import jakarta.servlet.http.HttpSession;

@Controller
public class AppointmentController {

    @Autowired
    AppRepo appRepo;

    @Autowired
    UserRepo usersRepo;

    @Autowired
    private HttpSession session;

    @GetMapping("/takeApp")
    public String openAppointmentPage(@ModelAttribute("app") Appointment app, Model model) {
        model.addAttribute("app", new Appointment());
        return "user/register_app";
    }

    @PostMapping("/submit_app")
    public String submitAppointment(@ModelAttribute Appointment app, Model model) {
        try {
            if (app.getUsers() == null) {
                model.addAttribute("err", "User is not associated with the appointment.");
                return "error"; // Handle the case where the user is not set
            }

            // Set additional properties (if needed)
            app.setStatus("pending");

            // Save the appointment
            Appointment savedAppointment = appRepo.save(app);

            if (savedAppointment != null) {
                model.addAttribute("succ", "Appointment Registered Successfully");
            } else {
                model.addAttribute("err", "Something went wrong on the server.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("err", "An error occurred during registration.");
        }

        return "redirect:/takeApp";
    }
}
