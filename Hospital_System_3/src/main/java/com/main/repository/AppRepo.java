package com.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main.entities.Appointment;

public interface AppRepo extends JpaRepository<Appointment, Long> {

}