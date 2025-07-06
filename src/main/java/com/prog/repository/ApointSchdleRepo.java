package com.prog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prog.entity.ApointSchdle;
import com.prog.entity.User;

public interface ApointSchdleRepo extends JpaRepository<ApointSchdle, Integer> {

	public List<ApointSchdle> findByUserOrderByIdDesc(User user);

}
