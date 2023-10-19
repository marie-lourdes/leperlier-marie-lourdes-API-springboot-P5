package com.safetynet.api.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.json.JsonArray;
import javax.json.JsonValue;

import org.springframework.stereotype.Component;

import com.safetynet.api.model.MedicalRecord;

@Component
public class ReadMedicalRecordDataFromFileImpl implements IDatasFileReader<MedicalRecord> {
	private JsonArray datasJsonMedicalRecords;
	private List<MedicalRecord> listOfMedicalRecords;

	@Override
	public List<MedicalRecord>  readFile() throws IOException {
		listOfMedicalRecords = new LinkedList<MedicalRecord>();

		// get JsonArray of data entity from JsonReader of Interface IDatasFileReader
		datasJsonMedicalRecords = readDataJson("medicalrecords");

		// create list linked of medicalRecords
		for (JsonValue elem : datasJsonMedicalRecords) {
			MedicalRecord medicalRecord = new MedicalRecord();
			medicalRecord.setId(
					elem.asJsonObject().getString("firstName") + " " + elem.asJsonObject().getString("lastName"));
			medicalRecord.setFirstName(elem.asJsonObject().getString("firstName"));
			medicalRecord.setLastName(elem.asJsonObject().getString("lastName"));
			medicalRecord.setBirthdate(elem.asJsonObject().getString("birthdate"));

			// create ArrayList of medications from JsonArray medications
			JsonArray medicationsArray = elem.asJsonObject().getJsonArray("medications");
			List<String> listOfMedications = new ArrayList<String>();

			int indexMedications = 0;
			for (JsonValue value : medicationsArray) {
				listOfMedications.add(indexMedications++, (String) value.toString());
			}
			medicalRecord.setMedications(listOfMedications);

			// create ArrayList of allergies from JsonArray allergies
			JsonArray allergiesArray = elem.asJsonObject().getJsonArray("allergies");
			List<String> listOfAllergies = new ArrayList<String>();

			int indexAllergies = 0;
			for (JsonValue value : allergiesArray) {
				listOfAllergies.add(indexAllergies++, (String) value.toString());
			}
			medicalRecord.setAllergies(listOfAllergies);
			
			
			
			listOfMedicalRecords.add(medicalRecord);
			System.out.println("element of medicalRecords" + elem.asJsonObject());

		}
		System.out.println("list of medicalRecords" + listOfMedicalRecords);

		return listOfMedicalRecords;
		

	}
}