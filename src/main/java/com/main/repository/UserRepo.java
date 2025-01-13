package com.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main.entities.Users;
import java.util.List;

public interface UserRepo extends JpaRepository<Users, Long> {

    Users findByEmail(String email);

}
