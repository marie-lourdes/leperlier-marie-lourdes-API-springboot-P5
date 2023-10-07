package com.safetynet.api.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.safetynet.api.model.Person;
import com.safetynet.api.service.ReadPersonDataFromFileImpl;
import com.safetynet.api.service.dataservice.PersonService;

@Component
public class PersonREADONLYRepositoryImpl implements IPersonREADONLYRepository {
	private List<Optional<Person>> listOfPersonsFoundById;
	private Optional<Person> personFoundById;
	/*
	 * @Autowired PersonService personService;
	 */

	@Autowired
	ReadPersonDataFromFileImpl readPersons;

	@Override
	public List<Person> findAll() throws IOException {
		return readPersons.readFile();
	}

	@Override
	public List<Optional<Person>> findByFirstNameAndLastName(String firstName, String lastName) {
		List<Person> persons = new ArrayList<Person>();
		listOfPersonsFoundById = new ArrayList<Optional<Person>>();
		personFoundById = Optional.empty();
		try {
			persons = readPersons.readFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		persons.forEach(elem -> {
			String firstNamePerson = elem.getFirstName();
			String lastNamePerson = elem.getLastName();
			if (firstNamePerson.equals(firstName) && lastNamePerson.equals(lastName)) {

				// test object by memory
				System.out.println("------------------element of persons AVANT modification en memoire------------" +elem.getEmail());
				elem.setEmail("email@modifié en memoire.com");
				System.out.println("----------------element of persons APRES modification en memoire---------------" +elem.getEmail());
				
				System.out.println("person found by first and last name of person :" + elem);

				personFoundById = Optional.ofNullable(elem);
				listOfPersonsFoundById.add(personFoundById);

			}
		});

		/*
		 * try { //System.out.println("test objetperson en memoire :"+personService.
		 * getPersonsFromFile()); } catch (IOException e) { // TODO Auto-generated catch
		 * block e.printStackTrace(); }
		 */
		System.out.println("listOfPersonsFoundByFullName :" + listOfPersonsFoundById);
		return listOfPersonsFoundById;
	}
	/*
	 * @Override public Optional<Person> findByName() { // TODO Auto-generated
	 * method stub return Optional.empty(); }
	 */

	@Override
	public Optional<Person> findById(String id) {
		List<Person> persons = new ArrayList<Person>();
		listOfPersonsFoundById = new ArrayList<Optional<Person>>();
		personFoundById = Optional.empty();
		try {
			persons = readPersons.readFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		persons.forEach(elem -> {
			String idPerson = elem.getId();

			if (idPerson.toString().equals(id.toString())) {
				/*
				 * elem.setId(firstNamePerson +lastNamePerson);
				 * System.out.println("city person AVANTmofification en memoire"
				 * +elem.getCity());
				 * 
				 * elem.setCity("ville de l objet memoire modifié");
				 * System.out.println("city person APRES en memoire :" +elem.getCity());
				 */
				personFoundById = Optional.ofNullable(elem);
			}
		});

		/*
		 * try { //System.out.println("test objetperson en memoire :"+personService.
		 * getPersonsFromFile()); } catch (IOException e) { // TODO Auto-generated catch
		 * block e.printStackTrace(); }
		 */
		System.out.println("personFoundById :" + 	personFoundById);
		return 	personFoundById;
	}
}
