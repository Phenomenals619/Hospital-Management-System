package com.prog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prog.entity.DoctorDetails;
import com.prog.entity.User;
import com.prog.model.RequestDoctorDetails;
import com.prog.repository.DoctorDtlsRepo;
import com.prog.repository.UserRepo;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private DoctorDtlsRepo doctRepo;

	@Override
	public List<User> getAllDoctor(String role) {
		return userRepo.findByRole(role);
	}

	@Override
	public User getUserById(int id) {
		return userRepo.findById(id).get();
	}

	@Override
	public boolean updateDoctorDetails(RequestDoctorDetails req) {

		User user = userRepo.findById(req.getId()).get();
		user.setFullName(req.getFullName());
		user.setEmail(req.getEmail());
		user.setMobNo(req.getMobNo());
		user.setEnable(req.isEnable());

		userRepo.save(user);

		DoctorDetails d = doctRepo.findById(req.getDid()).get();
		d.setSpecialist(req.getSpecialist());
		d.setDiseases(req.getDiseases());
		d.setDescription(req.getDescription());

		doctRepo.save(d);

		return true;
	}

}
