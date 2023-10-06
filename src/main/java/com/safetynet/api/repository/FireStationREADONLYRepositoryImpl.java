package com.safetynet.api.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.Person;
import com.safetynet.api.service.ReadFireStationDataFromFileImpl;

@Component
public class FireStationREADONLYRepositoryImpl implements IFireStationREADONLYRepository{

	private List<Optional<FireStation>> listOfFireStationsFoundByNumber;
	
	private Optional<FireStation> fireStationFoundByNumber;
	private Optional<FireStation> fireStationFoundById;
	private List<FireStation> fireStations=new ArrayList< FireStation>(); 
	private List<Optional<FireStation>> listOfFireStationsFoundById;
	@Autowired
	ReadFireStationDataFromFileImpl readFireStations;

	@Override
	public List<FireStation> findAll() throws IOException{
		fireStations=readFireStations.readFile();
		return fireStations;
	}
	
	@Override
	public List<Optional<FireStation>> findById(String id) {
		List<FireStation> fireStations= new ArrayList<FireStation>();
		listOfFireStationsFoundById = new ArrayList<Optional<FireStation>>();
		fireStationFoundById = Optional.empty();
		try {
			fireStations = readFireStations.readFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 fireStations.forEach(elem -> {
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
				fireStationFoundById = Optional.ofNullable(elem);
				System.out.println("personFoundById :" + 	fireStationFoundById );
			}
		});

		
		 System.out.println("listOfFireStationsFoundById  :" + listOfFireStationsFoundById );
		return 	listOfFireStationsFoundById ;
	}
	
	@Override
	public List<Optional<FireStation>>  findByStationNumber(String stationNumber){
		// fireStations = new ArrayList< FireStation>();
		
		 listOfFireStationsFoundByNumber = new ArrayList< Optional<FireStation>>();
		fireStationFoundByNumber= Optional.empty();
		/*try {
			fireStations = this.findAll();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		 fireStations.forEach(elem -> {
			String stationNumberOfFireStation = elem.getStationNumber();
			//String lastNamePerson = elem.getLastName();
			if (stationNumberOfFireStation.contains(stationNumber)) {
				System.out.println("element found by stationNumber" + elem);
				
				/*.orElseThrow(() -> new NullPointerException(
				" an error has occured,this firestation " + stationNumber + " doesn't exist, try again ")))*/
				//personService.deleteOnePersonByName(elem);
				
				fireStationFoundByNumber=Optional.ofNullable(elem);
				 listOfFireStationsFoundByNumber.add(fireStationFoundByNumber);
			}		 	
	});
		 System.out.println("listOfFireStationsFoundByNumber :" + listOfFireStationsFoundByNumber);
		 return listOfFireStationsFoundByNumber;
	}
	
}
