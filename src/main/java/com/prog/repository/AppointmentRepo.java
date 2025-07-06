package com.prog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prog.entity.Appointment;
import com.prog.entity.User;

public interface AppointmentRepo extends JpaRepository<Appointment, Integer> {

	public List<Appointment> findByDoctor(User user);

	public List<Appointment> findByUser(User user);

}
