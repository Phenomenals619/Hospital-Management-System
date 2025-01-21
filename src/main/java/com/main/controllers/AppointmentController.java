package com.main.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.main.repository.AppRepo;

@Controller
public class AppointmentController {

    @Autowired
    AppRepo appRepo;

    @GetMapping("/takeApp")
    public String openAppointmentPage(Model model) {
        return "user/register_app";
    }

}
