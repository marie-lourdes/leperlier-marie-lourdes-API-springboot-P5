package com.safetynet.api.model;

import java.util.List;

import com.safetynet.api.utils.RegexConstants;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class MedicalRecord {
	private String id;
	
	@NotNull
	private String firstName;
	
	@NotNull
	private String lastName;
	
	@NotNull
	@Pattern(regexp = RegexConstants.REGEX_DATE)
	private String birthdate;

	private List<String> medications;
	
	private List<String> allergies;

	public String getId() {
		return id;
	}

	public String setId(String id) {
		return this.id = id;
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

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
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

	@Override
	public String toString() {
		return "MedicalRecord{" + "id:" + id + ", first name:'" + firstName + '\'' + ", last name:" + lastName
				+ ", birthdate:" + birthdate + ", medication:" + medications + ", allergies:" + allergies + "}";
	}
}