package com.prog.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.prog.entity.User;
import com.prog.repository.DoctorDtlsRepo;
import com.prog.repository.UserRepo;
import com.prog.service.DoctorService;
import com.prog.service.UserService;

@Controller
public class HomeController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private DoctorDtlsRepo doctRepo;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private DoctorService doctService;

	@ModelAttribute
	public void addCommnData(Principal p, Model m) {
		if (p != null) {
			String email = p.getName();
			User user = userRepo.findByEmail(email);
			m.addAttribute("user", user);
		}

	}

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/signin")
	public String login() {
		return "login";
	}

	@GetMapping("/register")
	public String register() {
		return "register";
	}

	@GetMapping("/doctRegister")
	public String Doctregister() {
		return "doctor_register";
	}

	@PostMapping("/registerUser")
	public String saveUser(@ModelAttribute User user, HttpSession session) {

		if (userService.existEmail(user.getEmail())) {
			session.setAttribute("succMsg", "Email id alreday exists");
		} else {

			if (userService.register(user) != null) {
				session.setAttribute("succMsg", "Register sucessfully");
			} else {
				session.setAttribute("errorMsg", "something error in server");
			}

		}

		return "redirect:/register";
	}

	@PostMapping("/registerDoct")
	public String registerDoctor(@ModelAttribute User user, @RequestParam("file") MultipartFile file,
			HttpSession session) {

		user.setCertificate(file.getOriginalFilename());

		if (userService.doctRegister(user) != null) {
			try {
				File saveFile = new ClassPathResource("static/doctor_certificate").getFile();

				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
				// System.out.println(path);
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

			} catch (Exception e) {
				e.printStackTrace();
			}
			session.setAttribute("succMsg", "Register sucessfully");
		} else {
			session.setAttribute("errorMsg", "something error in server");
		}

		return "redirect:/doctRegister";
	}

	@GetMapping("/findDoctor")
	public String findDoctor(Model m) {
		m.addAttribute("doctorList", userRepo.findByRole("ROLE_DOCTOR"));
		return "search";
	}

	@PostMapping("/search")
	public String seach(@RequestParam("ch") String ch, Model m) {
		m.addAttribute("userService", userService);
		m.addAttribute("doct", userService.searchDoctor(ch));

		return "search";
	}

	@GetMapping("/viewDoct/{uid}/{did}")
	public String viewDoct(@PathVariable int uid, @PathVariable int did, Model m) {

		User us = userRepo.findById(uid).get();

		m.addAttribute("us", us);
		m.addAttribute("did", doctRepo.findById(did).get());
		m.addAttribute("appoint", doctService.getAllAppointSchedule(us));
		return "view_doctor";
	}

	@GetMapping("/loadforgotPassword")
	public String loadForgotPassword() {
		return "forgot_password";
	}

	@GetMapping("/loadresetPassword/{id}")
	public String loadResetPassword(@PathVariable int id, Model m) {
		m.addAttribute("uid", id);
		return "reset_password";
	}

	@PostMapping("/forgotPassword")
	public String forgotPassword(@RequestParam String email, @RequestParam String mobno, HttpSession session) {
		User u = userService.chechUserEmailAndMobNo(email, mobno);

		if (u != null) {
			return "redirect:/loadresetPassword/" + u.getId();

		} else {
			session.setAttribute("errorMsg", "invalid email & password");
			return "redirect:/loadforgotPassword";
		}
	}

	@PostMapping("/changePasswordx")
	public String changeNewPassword(@RequestParam String password, @RequestParam int uid, HttpSession session) {
		User user = userService.getUserById(uid);
		user.setPassword(passwordEncoder.encode(password));
		user.setId(uid);

		if (userService.updateUser(user) != null) {
			session.setAttribute("succMsg", "Password Change Sucessfully");
		} else {
			session.setAttribute("error", "Something wrong on server");
		}
		return "redirect:/loadforgotPassword";

	}

}
