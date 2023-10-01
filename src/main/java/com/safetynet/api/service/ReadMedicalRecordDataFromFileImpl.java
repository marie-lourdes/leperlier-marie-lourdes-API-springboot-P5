package com.safetynet.api.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

import org.springframework.stereotype.Component;

import com.safetynet.api.model.MedicalRecord;

@Component
public class ReadMedicalRecordDataFromFileImpl implements IDatasFileReader {
	private String path = "src/main/resources/datasSafetyNetAlerts.json";
	private JsonArray datasJsonMedicalRecords;
	private List<MedicalRecord> listOfMedicalRecords;

	@Override
	public List<MedicalRecord> readFile() throws IOException {
		InputStream is = new FileInputStream(path);
		JsonReader jsonReader = Json.createReader(is);
		 listOfMedicalRecords= new LinkedList<MedicalRecord>();
		
		// get JsonObject from JsonReader
		JsonObject datasJsonMedicalRecordsObject = jsonReader.readObject();
		datasJsonMedicalRecords= datasJsonMedicalRecordsObject.getJsonArray("medicalrecords");
		System.out.println("all datas Json MedicalRecord parsed with its arrays:" + datasJsonMedicalRecords);
		
		jsonReader.close();
		is.close();
		
		//create list linked of medicalRecords
		for( JsonValue elem : datasJsonMedicalRecords) {
			MedicalRecord medicalRecord =new MedicalRecord(); 
			medicalRecord.setFirstName( elem.asJsonObject().getString("firstName"));
			medicalRecord.setLastName( elem.asJsonObject().getString("lastName"));
			medicalRecord.setBirthdate( elem.asJsonObject().getString("birthdate"));
			List<String> medicationArray =	(List<String>) elem.asJsonObject().get("medications");
			medicalRecord.setMedications( medicationArray);
			List<String> allergiesArray =	(List<String>) elem.asJsonObject().get("allergies");
			medicalRecord.setAllergies( allergiesArray);
			
			
			 listOfMedicalRecords.add(medicalRecord);
			System.out.println("element of medicalRecords"+elem.asJsonObject());
			
		}
		// listOfMedicalRecords.add();
		System.out.println("list of medicalRecords"+ listOfMedicalRecords);

		return  listOfMedicalRecords ;
		// return datasJsonPersonParsed ;
	
	}
}