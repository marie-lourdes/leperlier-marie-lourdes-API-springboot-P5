package com.safetynet.api.service.dataservice;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.api.model.FireStation;
import com.safetynet.api.repository.FireStationREADONLYRepositoryImpl;
import com.safetynet.api.repository.IFireStationCRUDRepository;

@Service
public class FireStationService {
	@Autowired
	private IFireStationCRUDRepository fireStationRepository;

	@Autowired
	private FireStationREADONLYRepositoryImpl fireStationRepositoryFile;

//--------------------repository avec source de donnéees BDD---------
	
	  public List<FireStation> getAllFireStations() { return (List<FireStation>)
	  fireStationRepository.findAll(); }
	 

//--------------------repository avec source de donnéees fichier Json---------		
	public List<FireStation> getFireStationsFromFile() throws IOException {
		return (List<FireStation>) fireStationRepositoryFile.findAll();
	}

/*	public Optional<FireStation> getOneFireStationById(Long id) {
		Optional<FireStation> fireStationFoundById = Optional
				.ofNullable(fireStationRepository.findById(id).orElseThrow(() -> new NullPointerException(
						" an error has occured,this firestation " + id + " doesn't exist, try again ")));
		return fireStationFoundById;
	}*/
	
/*	public List< Optional<FireStation>> getOneFireStationByNumber(String stationNumber) {
		//List< Optional<FireStation>> fireStationFoundByNumber=
				/* Optional
				.ofNullable(fireStationRepositoryFile.findByStationNumber(stationNumber)).orElseThrow(() -> new NullPointerException(
						" an error has occured,this firestation " + stationNumber + " doesn't exist, try again "));*/
		/*return fireStationRepositoryFile.findByStationNumber(stationNumber);
	}*/

	public FireStation saveFireStation(FireStation fireStation) {
		return fireStationRepository.save(fireStation);
	}

	public FireStation updateOneFireStationById(FireStation fireStation, Long id) {
		return fireStationRepository.save(fireStation);
	}

	public void deleteStationNumberFireStation(FireStation fireStation, Long id) {
		fireStationRepository.deleteById(id);
		/*
		 * List<String> listOfFireStation = new ArrayList<String>();
		 * listOfFireStation.add(fireStation.getStationNumber().toString());
		 * listOfFireStation.add(fireStation.getAddress()); listOfFireStation.remove(0);
		 * return listOfFireStation; //fireStationRepository.deleteById(id);
		 */
	}
}
