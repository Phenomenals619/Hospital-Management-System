package com.prog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prog.entity.ApointSchdle;
import com.prog.entity.User;
import com.prog.repository.ApointSchdleRepo;

@Service
public class DoctorServiceImpl implements DoctorService {

	@Autowired
	private ApointSchdleRepo appointRepo;

	@Override
	public ApointSchdle addApointSchedule(ApointSchdle ap) {

		return appointRepo.save(ap);
	}

	@Override
	public List<ApointSchdle> getAllAppointSchedule(User user) {
		return appointRepo.findByUserOrderByIdDesc(user);
	}

}
