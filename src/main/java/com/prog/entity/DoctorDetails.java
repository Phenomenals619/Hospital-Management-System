package com.prog.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class DoctorDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String specialist;

	private String diseases;

	private String description;

	private String profile;

	private String address;

	private String doctorCharges;

	public DoctorDetails(String specialist, String diseases, String description, String profile, String address,
			String doctorCharges) {
		super();
		this.specialist = specialist;
		this.diseases = diseases;
		this.description = description;
		this.profile = profile;
		this.address = address;
		this.doctorCharges = doctorCharges;
	}

	public DoctorDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getDoctorCharges() {
		return doctorCharges;
	}

	public void setDoctorCharges(String doctorCharges) {
		this.doctorCharges = doctorCharges;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSpecialist() {
		return specialist;
	}

	public void setSpecialist(String specialist) {
		this.specialist = specialist;
	}

	public String getDiseases() {
		return diseases;
	}

	public void setDiseases(String diseases) {
		this.diseases = diseases;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

}
