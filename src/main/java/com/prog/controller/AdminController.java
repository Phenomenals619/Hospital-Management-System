package com.prog.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

import com.prog.entity.User;
import com.prog.model.RequestDoctorDetails;
import com.prog.repository.UserRepo;
import com.prog.service.AdminService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	public static int BUFFER_SIZE = 1024 * 100;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private AdminService adminService;

	@ModelAttribute
	public void addCommnData(Principal p, Model m) {
		if (p != null) {
			String email = p.getName();
			User user = userRepo.findByEmail(email);
			m.addAttribute("user", user);
		}

	}

	@GetMapping("/")
	public String home() {
		return "admin/index";
	}

	@GetMapping("/doctorList")
	public String doctStatus(Model m) {
		m.addAttribute("doct", adminService.getAllDoctor("ROLE_DOCTOR"));
		return "admin/doctor";
	}

	@GetMapping("/getDoct/{id}")
	public String getDoctById(Model m, @PathVariable int id) {
		m.addAttribute("doct", adminService.getUserById(id));
		return "admin/edit_doctor";
	}

	@GetMapping("/specialist")
	public String specialist() {
		return "admin/specialist";
	}

	@GetMapping("/editSpec")
	public String editSpec() {
		return "admin/edit_spec";
	}

	@PostMapping("/updateDoctor")
	public String updateDoctorDetails(@ModelAttribute RequestDoctorDetails req, HttpSession session) {

		if (adminService.updateDoctorDetails(req)) {
			session.setAttribute("msg", "Update Sucessfully");
		}

		return "redirect:/admin/doctorList";
	}

	@GetMapping("/download/{name}")
	public void downloadCertificate(@PathVariable String name, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		OutputStream outStream = null;
		FileInputStream inputStream = null;
		try {
			File saveFile = new ClassPathResource("static/doctor_certificate").getFile();

			Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + name);

			File file = new File(path + "");

			if (file.exists()) {

				/**** Setting The Content Attributes For The Response Object ****/

				String mimeType = "application/octet-stream";
				response.setContentType(mimeType);

				/**** Setting The Headers For The Response Object ****/

				String headerKey = "Content-Disposition";
				String headerValue = String.format("attachment; filename=\"%s\"", file.getName());
				response.setHeader(headerKey, headerValue);

				/**** Get The Output Stream Of The Response ****/

				outStream = response.getOutputStream();
				inputStream = new FileInputStream(file);
				byte[] buffer = new byte[BUFFER_SIZE];
				int bytesRead = -1;

				/****
				 * Write Each Byte Of Data Read From The Input Stream Write Each Byte Of Data
				 * Read From The Input Stream Into The Output Stream
				 ***/
				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outStream.write(buffer, 0, bytesRead);
				}
			}
		} catch (IOException ioExObj) {
			System.out.println("Exception While Performing The I/O Operation?= " + ioExObj.getMessage());
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}

			outStream.flush();
			if (outStream != null) {
				outStream.close();
			}
		}

	}
}
