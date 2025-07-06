package com.prog.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.annotation.ApplicationScope;

import com.prog.entity.ApointSchdle;
import com.prog.entity.Appointment;
import com.prog.entity.User;
import com.prog.repository.ApointSchdleRepo;
import com.prog.repository.AppointmentRepo;
import com.prog.repository.UserRepo;
import com.prog.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ApointSchdleRepo appRepo;

	@Autowired
	private AppointmentRepo appintRepo;

	@Autowired
	private UserService userService;

	@ModelAttribute
	public void addCommnData(Principal p, Model m) {
		if (p != null) {
			String email = p.getName();
			User user = userRepo.findByEmail(email);
			m.addAttribute("user", user);
		}

	}

	@GetMapping("/appointment/{did}/{aid}")
	public String loadAppointPage(Model m, @PathVariable int did, @PathVariable int aid) {
		System.out.println(did);
		m.addAttribute("doctor", userRepo.findById(did).get());
		m.addAttribute("appoint", appRepo.findById(aid).get());

		return "user/appoint";
	}

	@PostMapping("/cnfmAppoint")
	public String confirmAppointment(@ModelAttribute Appointment ap, @RequestParam("did") int did,
			@RequestParam("uid") int uid, @RequestParam("sid") int sid, HttpSession session) {

		User us = userRepo.findById(uid).get();
		User doct = userRepo.findById(did).get();
		ApointSchdle apsch = appRepo.findById(sid).get();

		ap.setStatus("Not Completed");
		ap.setDoctor(doct);
		ap.setUser(us);
		ap.setSchedule(apsch);

		if (apsch.getSlot() > 0) {
			if (userService.saveAppoint(ap) != null) {
				userService.sendMail(ap);
				userService.doctorMail(ap);
				apsch.setSlot(apsch.getSlot() - 1);
				appRepo.save(apsch);

				return "redirect:/user/appointSucc";
			} else {
				session.setAttribute("errorMsg", "Something wrong on server");
				return "redirect:/user/appointment/" + did + "/" + sid;
			}
		} else {
			session.setAttribute("errorMsg", "Slot Not Available");
			return "redirect:/user/appointment/" + did + "/" + sid;
		}

	}

	@GetMapping("/appointSucc")
	public String appointSuccess() {
		return "user/appoint_success";
	}

	@GetMapping("/getMyAppoint")
	public String myAppoint(Principal p, Model m) {

		String email = p.getName();
		User user = userRepo.findByEmail(email);

		m.addAttribute("apntLi", appintRepo.findByUser(user));

		return "user/my_appoint";
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

}
