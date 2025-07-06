package com.prog.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.prog.entity.ApointSchdle;
import com.prog.entity.Appointment;
import com.prog.entity.DoctorDetails;
import com.prog.entity.User;
import com.prog.repository.AppointmentRepo;
import com.prog.repository.DoctorDtlsRepo;
import com.prog.repository.UserRepo;
import com.prog.service.DoctorService;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private DoctorService doctService;

	@Autowired
	private DoctorDtlsRepo doctDtlsRepo;

	@Autowired
	private AppointmentRepo appointRepo;

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
		return "doctor/index";
	}

	@GetMapping("/addAppoint")
	public String appoint(Model m, Principal p) {
		String email = p.getName();
		User user = userRepo.findByEmail(email);
		m.addAttribute("appList", doctService.getAllAppointSchedule(user));
		return "doctor/add_appoint";
	}

	@PostMapping("/registerApoint")
	public String addAppointment(@ModelAttribute ApointSchdle ap, HttpSession session, Principal p) {

		String email = p.getName();
		User user = userRepo.findByEmail(email);
		ap.setUser(user);

		if (doctService.addApointSchedule(ap) != null) {
			session.setAttribute("succMsg", "Appoint Schedule Added");
		} else {
			session.setAttribute("errorMsg", "Server Problem");
		}

		return "redirect:/doctor/addAppoint";
	}

	@GetMapping("/editProfile")
	public String loadEditProfile() {
		return "doctor/edit_profile";
	}

	@PostMapping("/updateProfile")
	public String updateProfile(@ModelAttribute DoctorDetails dd, @RequestParam("pf") MultipartFile pf,
			HttpSession session) {

		DoctorDetails ddtls = doctDtlsRepo.findById(dd.getId()).get();
		ddtls.setAddress(dd.getAddress());
		ddtls.setDoctorCharges(dd.getDoctorCharges());
		ddtls.setDescription(dd.getDescription());

		if (!pf.isEmpty()) {
			ddtls.setProfile(pf.getOriginalFilename());
		}

		if (doctDtlsRepo.save(ddtls) != null) {
			if (!pf.isEmpty()) {
				try {
					File saveFile = new ClassPathResource("static/doct_img").getFile();

					Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + pf.getOriginalFilename());
					// System.out.println(path);
					Files.copy(pf.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			session.setAttribute("msg", "Profile Update Sucessfully");
		}

		return "redirect:/doctor/";
	}

	@GetMapping("/patient")
	public String patient(Principal p, Model m) {
		String email = p.getName();
		User user = userRepo.findByEmail(email);
		m.addAttribute("patient", appointRepo.findByDoctor(user));
		return "doctor/patient";
	}
	
	@GetMapping("/statusUpdate/{id}")
	public String appointStats(@PathVariable int id,HttpSession session)
	{
		Appointment ap=appointRepo.findById(id).get();
		ap.setStatus("Completed");
		appointRepo.save(ap);
		session.setAttribute("succMsg", "Status Updated");
		return "redirect:/doctor/patient";
	}

	@GetMapping("/payment")
	public String payment() {
		return "payment";
	}
	

}
