package com.prog.service;

import java.util.List;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.prog.entity.Appointment;
import com.prog.entity.DoctorDetails;
import com.prog.entity.User;
import com.prog.repository.AppointmentRepo;
import com.prog.repository.DoctorDtlsRepo;
import com.prog.repository.UserRepo;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private DoctorDtlsRepo doctRepo;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private AppointmentRepo appRepo;

	@Autowired
	private JavaMailSender mailSender;

	@Override
	public User register(User user) {

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		return userRepo.save(user);
	}

	@Override
	public boolean existEmail(String email) {
		return userRepo.existsByEmail(email);
	}

	@Override
	public User doctRegister(User user) {

		DoctorDetails d = new DoctorDetails("NA", "NA", "NA", "default.png", "NA", "NA");
		doctRepo.save(d);

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setDoctDtls(d);

		return userRepo.save(user);
	}

	@Override
	public List<DoctorDetails> searchDoctor(String ch) {
		return doctRepo.findDoctorBySearch(ch, ch);
	}

	@Override
	public User getUserByDoctDtails(DoctorDetails d) {
		return userRepo.findByDoctDtls(d);
	}

	@Override
	public Appointment saveAppoint(Appointment ap) {
		return appRepo.save(ap);
	}

	@Override
	public void sendMail(Appointment ap) {

		String content = "Dear [[name]],<br>"
				+ "<h4>Your Appointment Sucessfully </h4> <b>Doctor Name :</b> [[doctName]] <br><b>Date :</b>[[date]] <br> <b>Time :</b> [[time]] <br><b>Address :</b> [[address]]"
				+ "<br><br><b>Thank you</b>";

		try {

			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);

			helper.setFrom("daspabitra55@gmail.com", "MediHome");
			helper.setTo(ap.getEmail());
			helper.setSubject("Appointment Sucessfully");

			content = content.replace("[[name]]", ap.getPatientName());

			content = content.replace("[[doctName]]", ap.getDoctor().getFullName());

			content = content.replace("[[date]]", ap.getSchedule().getDate());

			content = content.replace("[[time]]", ap.getSchedule().getFromTime() + "-" + ap.getSchedule().getToTime());

			content = content.replace("[[address]]", ap.getDoctor().getDoctDtls().getAddress());

			// System.out.println(content);

			helper.setText(content, true);
			mailSender.send(message);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void doctorMail(Appointment ap) {
		String content = "Dear [[name]],<br>"
				+ "<h4>Patient Details </h4> <b>Patient Name :</b> [[patientName]] <br><b>Date :</b>[[date]] <br> <b>Time :</b> [[time]] "
				+ "<br><br><b>Thank you</b>";

		try {

			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);

			helper.setFrom("daspabitra55@gmail.com", "MediHome");
			helper.setTo(ap.getDoctor().getEmail());
			helper.setSubject("Appointment Sucessfully");

			content = content.replace("[[name]]", ap.getDoctor().getFullName());

			content = content.replace("[[patientName]]", ap.getPatientName());

			content = content.replace("[[date]]", ap.getSchedule().getDate());

			content = content.replace("[[time]]", ap.getSchedule().getFromTime() + "-" + ap.getSchedule().getToTime());


			// System.out.println(content);

			helper.setText(content, true);
			mailSender.send(message);

		} catch (Exception e) {
			e.printStackTrace();
		}


	}

	@Override
	public User getUserById(int uid) {

		return userRepo.findById(uid).get();
	}

	@Override
	public User updateUser(User user) {

		return userRepo.save(user);
	}

	@Override
	public User chechUserEmailAndMobNo(String email, String mobno) {

		return userRepo.findByEmailAndMobNo(email, mobno);
	}

}
