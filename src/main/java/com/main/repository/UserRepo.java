package com.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main.entities.Users;

public interface UserRepo extends JpaRepository<Users, Long>{

}
