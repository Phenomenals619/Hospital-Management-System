package com.main.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.main.entities.Doctor;
import com.main.repository.DoctorRepo;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DoctorController {

    @Autowired
    DoctorRepo doctorRepo;

    @GetMapping("/doctor")
    public String doctorLogin(@ModelAttribute("doctor") Doctor doc, Model model) {

        model.addAttribute("doctor", doc);
        return "doctor/doctor_login";
    }

    @PostMapping("/submit_login_doctor")
    public String submitLoginDoctor(@ModelAttribute("doctor") Doctor doc, Model model) {

        return "redirect:/";
    }

    @GetMapping("/addDoctor")
    public String addDoctors(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "doctor/doctor_register";
    }

    @PostMapping("/submit_register_doctor")
    public String docRegister(@ModelAttribute Doctor doc, Model model) {
        Doctor doctor = doctorRepo.save(doc);

        return "redirect:/";
    }

    @GetMapping("/viewDoctor")
    public String viewDoctors(Model model) {
        // Fetch all doctors from the repository
        List<Doctor> doctors = doctorRepo.findAll();

        // Add the list of doctors to the model
        model.addAttribute("doctors", doctors);

        return "doctor/view_doctor";
    }
}
