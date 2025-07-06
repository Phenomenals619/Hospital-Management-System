package com.prog.service;

import java.util.List;

import com.prog.entity.ApointSchdle;
import com.prog.entity.User;

public interface DoctorService {

	public ApointSchdle addApointSchedule(ApointSchdle ap);

	public List<ApointSchdle> getAllAppointSchedule(User user);

}
