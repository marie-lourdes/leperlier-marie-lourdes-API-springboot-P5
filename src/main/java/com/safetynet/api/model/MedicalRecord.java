package com.safetynet.api.model;

import java.util.List;

import com.safetynet.api.utils.RegexConstants;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
@Data
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

	public MedicalRecord() {
	}

	public MedicalRecord(String firstName, String lastName, String birthdate, List<String> medications,
			List<String> allergies) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthdate = birthdate;
		this.medications = medications;
		this.allergies = allergies;
	}

	public MedicalRecord(List<String> medications, List<String> allergies) {
		this.medications = medications;
		this.allergies = allergies;
	}

	@Override
	public String toString() {
		return "MedicalRecord{" + "id:" + id + ", first name:'" + firstName + '\'' + ", last name:" + lastName
				+ ", birthdate:" + birthdate + ", medication:" + medications + ", allergies:" + allergies + "}";
	}
}