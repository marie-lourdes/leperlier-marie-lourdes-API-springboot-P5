package com.safetynet.api.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "medicalrecords")
public class MedicalRecord {
	// this regex date require 0 in format date
	private final String REGEX_P = "^[0-3][0-9]/[0-3][0-9]/(?:[0-9][0-9])?[0-9][0-9]$";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	//@Pattern(regexp = REGEX_P)
	@Column(name = "birthdate")
	private Date birthdate;

	@Column(name = "medications")
	private List<String> medications;

	@Column(name = "allergies")
	private List<String> allergies;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthdate() {
		/* DateFormat format = new SimpleDateFormat("MM/dd/ yyyy");
		 try {
	           this.birthdate = format.format(date);
	            System.out.println(this.birthdate);
	        }
	        catch (ParseException e) {
	            e.printStackTrace();
	        }*/
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		
		this.birthdate = birthdate  ;
	}

	public List<String> getMedications() {
		return medications;
	}

	public void setMedications(List<String> medications) {
		this.medications = medications;
	}

	public List<String> getAllergies() {
		return allergies;
	}

	public void setAllergies(List<String> allergies) {
		this.allergies = allergies;
	}

}