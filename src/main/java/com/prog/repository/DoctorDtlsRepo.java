package com.prog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.prog.entity.DoctorDetails;
import com.prog.entity.User;

public interface DoctorDtlsRepo extends JpaRepository<DoctorDetails, Integer> {

	@Query("SELECT d FROM DoctorDetails d where d.specialist like %:specialist% or d.diseases like %:diseases%")
	public List<DoctorDetails> findDoctorBySearch(String specialist, String diseases);

}
