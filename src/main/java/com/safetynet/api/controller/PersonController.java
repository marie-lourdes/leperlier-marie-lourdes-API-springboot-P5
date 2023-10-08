package com.safetynet.api.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.api.UploadDataFileRunner;
import com.safetynet.api.model.Person;
import com.safetynet.api.repository.PersonRepositoryImpl;
import com.safetynet.api.service.ReadPersonDataFromFileImpl;
import com.safetynet.api.service.dataservice.PersonService;

@RestController
//@RequestMapping(path = "/api) // This means URL's start with /demo (after Application path)
public class PersonController {

	@Autowired
	private PersonService personService;
	
	
	//test modif objet en memoire dans Method HTTP PUT
	@Autowired
	PersonRepositoryImpl  personRepositoryFile;
	@Autowired
	UploadDataFileRunner uploadDataFileRunner ;
	@Autowired
	ReadPersonDataFromFileImpl readPerson;
	private List<Person> persons ;
	 
	/*@PostMapping("/person/")
	public ResponseEntity<Person> createPerson(@Valid @RequestBody Person person) {
//try {	
		personService.savePerson(person);
		System.out.println(person);
		return ResponseEntity.status(HttpStatus.CREATED).body(person);*/

//}
		/*
		 * catch(jakarta.validation.ConstraintViolationException e) { return
		 * e.getMessage(); }
		 
	}*/

	// -----------------requete a partir du fichier json-------------
	@GetMapping("/person/")
	public @ResponseBody List<Person> getAllPersonsFromFile() throws IOException {
		persons= readPerson.getListOfPersons();
	/*	try {
		
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}*/

		return persons;
	}

	@GetMapping("/person/{id}")
	public Optional<Person> getOnePerson(@PathVariable String id) {
		return personService.getOnePersonById(id);
	}

//----Person  getbyFullName  from file json------
	@GetMapping("/person")
	public List<Optional<Person>> getOnePersonByFullName(@RequestParam String firstName,
			@RequestParam String lastName) {
		return personService.getOnePersonByFullName(firstName, lastName);
	}

	@PutMapping("/person/{id}")
	public ResponseEntity<Optional<Person>> updateOnePersonById(@RequestBody Person person,@PathVariable String id) throws IOException{
		 Optional<Person> personFoundById=personService.getOnePersonById(id);
	/*	System.out.println("------------------ person found by name  AVANT modification en memoire------------" +personFoundById.get().getEmail());
		personFoundById.get().setEmail("email@modifié en memoire.com");
		System.out.println("----------------persons APRES modification en memoire---------------" +personFoundById.get().getEmail());*/
		//Optional<Person> personUpdated= personRepositoryFile.modify(id);
		if (id.toString().equals(personFoundById.get().getId().toString())) {
			 personFoundById.get().setAddress(person.getAddress());
			 personFoundById.get().setZip(person.getZip());
			 personFoundById.get().setCity(person.getCity()); 
			 personFoundById.get().setPhone(person.getPhone());
			 personFoundById.get().setEmail(person.getEmail());
		}
		
		persons=personService.getPersonsFromFile();
		Person index =persons.set(0,personFoundById.get());
		//System.out.println("index"+index);
		//persons.add(0,personFoundById.get());
		System.out.println("list of personUpdated"+persons );
		//personService.savePerson(personFoundById.get());
		return ResponseEntity.status(HttpStatus.CREATED).body(personFoundById );
	}
	// the id, first and last name cannot be modified
	/*
	 * @PutMapping("/person/{id}") public ResponseEntity<Optional<Person>>
	 * updateOnePersonById(@RequestBody Person person, @PathVariable Long id) {
	 * Optional<Person> personFoundByFullName = personService.getOnePersonById(id);
	 * 
	 * if (id.toString().equals(personFoundByFullName .get().getId().toString())) {
	 * personFoundByFullName .get().setAddress(person.getAddress());
	 * personFoundByFullName .get().setZip(person.getZip()); personFoundByFullName
	 * .get().setCity(person.getCity()); personFoundByFullName
	 * .get().setPhone(person.getPhone()); personFoundByFullName
	 * .get().setEmail(person.getEmail());
	 * 
	 * personService.savePerson(personFoundByFullName .get());
	 * System.out.println(personFoundByFullName ); } return
	 * ResponseEntity.status(HttpStatus.CREATED).body(personFoundByFullName ); }
	 */

	// the id, first and last name cannot be modified
	/*
	 * @PutMapping("/person") public ResponseEntity<List<Optional<Person>>>
	 * updateOnePersonByFullName(@RequestBody Person person, @RequestParam String
	 * firstName, @RequestParam String lastName) { List<Optional<Person>>
	 * personFoundByFullName
	 * =personService.getOnePersonByFullName(firstName,lastName); ///listIterator
	 * suport les operation CRUD contrairemnt a Iterator Iterator<Optional<Person>>
	 * iteratorPerson =personFoundByFullName.listIterator();
	 * 
	 * while(iteratorPerson.hasNext()) { Optional<Person> personItr =
	 * iteratorPerson.next();
	 * 
	 * if (firstName.toString().equals(personItr.get().getFirstName().toString()) &&
	 * lastName.toString().equals(personItr.get().getLastName())) {
	 * personItr.get().setAddress(person.getAddress());
	 * personItr.get().setZip(person.getZip());
	 * personItr.get().setCity(person.getCity()); personItr
	 * .get().setPhone(person.getPhone());
	 * personItr.get().setEmail(person.getEmail()); //personFoundByFullName.
	 * personService.savePerson(personItr.get());
	 * System.out.println(personFoundByFullName ); } }
	 * 
	 * return ResponseEntity.status(HttpStatus.CREATED).body(personFoundByFullName
	 * ); }
	 */

	/*@DeleteMapping("/person/")
	public ResponseEntity<Long> deleteOnePersonByName(@RequestParam String firstName, @RequestParam String lastName) {
		// List<Person> persons = personService.getAllPersons();

		// ---refactoring avec method get all person from file----
		List<Person> persons = new ArrayList<Person>();
		try {
			persons = personService.getPersonsFromFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		persons.forEach(elem -> {
			String firstNamePerson = elem.getFirstName();
			String lastNamePerson = elem.getLastName();
			if (firstNamePerson.contains(firstName) && lastNamePerson.contains(lastName)) {
				System.out.println("element to remove" + elem);
				personService.deleteOnePersonByName(elem);
			}
		});
		return new ResponseEntity<Long>(HttpStatus.NO_CONTENT);
	}*/
}