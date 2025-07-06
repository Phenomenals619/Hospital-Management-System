package com.prog.service;

import java.util.List;

import com.prog.entity.Appointment;
import com.prog.entity.DoctorDetails;
import com.prog.entity.User;

public interface UserService {

	public User register(User user);

	public boolean existEmail(String email);

	public User doctRegister(User user);

	public List<DoctorDetails> searchDoctor(String ch);

	public User getUserByDoctDtails(DoctorDetails d);

	public Appointment saveAppoint(Appointment ap);

	public void sendMail(Appointment ap);

	User getUserById(int uid);

	User updateUser(User user);

	User chechUserEmailAndMobNo(String email, String mobno);

	public void doctorMail(Appointment ap);

}
