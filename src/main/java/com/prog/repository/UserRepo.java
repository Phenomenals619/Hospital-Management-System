package com.prog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prog.entity.DoctorDetails;
import com.prog.entity.User;

public interface UserRepo extends JpaRepository<User, Integer> {

	public boolean existsByEmail(String email);

	
	public User findByEmail(String email);
	
	public List<User> findByRole(String role);
	
	public User findByDoctDtls(DoctorDetails d);
	
	public User findByEmailAndMobNo(String email, String mobNo);

}
