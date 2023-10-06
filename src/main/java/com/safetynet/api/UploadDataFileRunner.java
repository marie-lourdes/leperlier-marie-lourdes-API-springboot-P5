package com.safetynet.api;

<<<<<<< Updated upstream
<<<<<<< Updated upstream
=======
import java.io.IOException;
>>>>>>> Stashed changes
=======
import java.io.IOException;
>>>>>>> Stashed changes
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

<<<<<<< Updated upstream
<<<<<<< Updated upstream
import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.Person;
import com.safetynet.api.service.dataservice.FireStationService;
import com.safetynet.api.service.dataservice.MedicalRecordService;
import com.safetynet.api.service.dataservice.PersonService;

public class UploadDataFileRunner implements CommandLineRunner {
	/* private List<FireStation>fireStations ;
	private  List<MedicalRecord>medicalRecords;
	private  List<Person>persons;*/
	 
 @Autowired
 PersonService  personService;
 
 @Autowired
 FireStationService  fireStationService;
 
 @Autowired
 MedicalRecordService  medicalRecordService;

	@Override
	public void run(String... args) throws Exception {
		//use to logging each element of file json 
		personService.getPersonsFromFile();
	   	fireStationService.getFireStationsFromFile();
	 medicalRecordService.getMedicalRecordsFromFile();
	   // fireStationService.deleteByStationNumberFireStation("3");
	  //  System.out.println("firestation APRES SUPPRESION "+	fireStationService.getFireStationsFromFile());
	   //fireStationService.getFireStationsFromFile();
	
		fireStationService.getFireStationsByNumber("3");
		personService.getOnePersonById("Tenley Boyd");
		medicalRecordService.getOneMedicalRecordById("Tenley Boyd");
	}
/* public List<FireStation> getFireStation () {
	 return fireStations;
 }
 public List<FireStation> setFireStation ( List<FireStation> firestations) {
	 return this.fireStations= firestations ;
 }
 
 public List<Person> getPerson () {
	 return persons;
 }
 public List<Person> setPerson ( List<Person> persons) {
	 return this.persons= persons ;
 }
 
 public List<MedicalRecord> getMedicalRecord() {
	 return medicalRecords;
 }
 public  List<MedicalRecord> setMedicalRecord ( List<MedicalRecord> medicalRecords) {
	 return this.medicalRecords= medicalRecords ;
 }*/
 
=======
import com.safetynet.api.model.Person;
import com.safetynet.api.service.UploadDataFileService;
import com.safetynet.api.service.dataservice.PersonService;

@Component
public class UploadDataFileRunner implements CommandLineRunner {

	@Autowired
	UploadDataFileService uploadDataFileService;

	@Autowired
	PersonService personService;

	private List<Person> personsFromFile;
	private List<Person> persons;
	public UploadDataFileRunner() {
	}

	@Override
	public void run(String... args) throws Exception {
		
		try {
			personsFromFile = uploadDataFileService.getPersonsFromFile();
	
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	 persons = personService.getAllPersons();
		
		for(Person person : personsFromFile) {
			persons.forEach(elem -> {
				String firstNamePerson = elem.getFirstName();
				String lastNamePerson = elem.getLastName();
				if (!firstNamePerson.equals(person.getFirstName()) && ! lastNamePerson.equals(person.getFirstName())) {
				
					personService.savePerson(person);
					//personService.deleteOnePersonByName(elem);
				}else {
					//personsFromFile.get(0);
					System.out.println("element du fichier deja enregistré dans la bdd" + elem);	
					return;
				
					
				}
			});
			
		}

=======
import com.safetynet.api.model.Person;
import com.safetynet.api.service.UploadDataFileService;
import com.safetynet.api.service.dataservice.PersonService;

@Component
public class UploadDataFileRunner implements CommandLineRunner {

	@Autowired
	UploadDataFileService uploadDataFileService;

	@Autowired
	PersonService personService;

	private List<Person> personsFromFile;
	private List<Person> persons;
	public UploadDataFileRunner() {
	}

	@Override
	public void run(String... args) throws Exception {
		
		try {
			personsFromFile = uploadDataFileService.getPersonsFromFile();
	
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	 persons = personService.getAllPersons();
		
		for(Person person : personsFromFile) {
			persons.forEach(elem -> {
				String firstNamePerson = elem.getFirstName();
				String lastNamePerson = elem.getLastName();
				if (!firstNamePerson.equals(person.getFirstName()) && ! lastNamePerson.equals(person.getFirstName())) {
				
					personService.savePerson(person);
					//personService.deleteOnePersonByName(elem);
				}else {
					//personsFromFile.get(0);
					System.out.println("element du fichier deja enregistré dans la bdd" + elem);	
					return;
				
					
				}
			});
			
		}

>>>>>>> Stashed changes

		//uploadDataFileService.getPersonsFromFile();
	
		uploadDataFileService.getFireStationsFromFile();
		// uploadDataService.getMedicalRecordsFromFile();
	}
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
}
