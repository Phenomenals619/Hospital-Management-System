package com.prog.service;

import java.util.List;

import com.prog.entity.User;
import com.prog.model.RequestDoctorDetails;

public interface AdminService {

	public List<User> getAllDoctor(String role);

	public User getUserById(int id);

	public boolean updateDoctorDetails(RequestDoctorDetails req);

}
