package com.safetynet.api;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

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
		

		//uploadDataFileService.getPersonsFromFile();
	
		uploadDataFileService.getFireStationsFromFile();
		// uploadDataService.getMedicalRecordsFromFile();

}


	
}

