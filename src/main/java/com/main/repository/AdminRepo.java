package com.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main.entities.Admin;
import java.util.List;

public interface AdminRepo extends JpaRepository<Admin, Integer> {

    Admin findByEmail(String email);

}
