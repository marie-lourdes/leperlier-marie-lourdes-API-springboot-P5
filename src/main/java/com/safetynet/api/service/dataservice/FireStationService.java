package com.safetynet.api.service.dataservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.safetynet.api.model.FireStation;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FireStationService {
	private final List<FireStation> fireStations = new ArrayList<>();

	public FireStation addFireStation(FireStation fireStation) {
		fireStation.setId(fireStation.getStationNumber());
		fireStations.add(fireStation);
		return fireStation;
	}
	
    public FireStation updateFireStation(String id,FireStation updatedFireStation) {
        return fireStations .stream()
                .filter(fireStation -> fireStation.getId().equals(id) )
                .findFirst()
                .map(existingFireStation-> {
                	existingFireStation.setStationNumber(updatedFireStation.getStationNumber());
                	existingFireStation.setAddress(updatedFireStation.getAddress());          
                    return existingFireStation;
                })
                .orElse(null);
    }
    
	public Optional<FireStation> getFireStationsById(String id) {
		return fireStations.stream().filter(fireStation -> fireStation.getId().equals(id)).findFirst()
				.map(existingFireStation -> {
					return existingFireStation;
				});
	}

	public Optional<FireStation> getOneFireStationByAddress(String address) {
		return fireStations.stream().filter(fireStation -> fireStation.getAddress().equals(address)).findFirst()
				.map(existingFireStation -> {
					return existingFireStation;
				});
	}

	public List<FireStation> getAllFireStations() {
		System.out.println("Retrieving all persons" + fireStations);
		return fireStations;
	}

//--------------------repository avec source de donnéees fichier Json---------		
	/*
	 * public List<FireStation> getFireStationsFromFile() throws IOException {
	 * return (List<FireStation>) fireStationRepositoryFile.findAll(); }
	 */

	/*
	 * public Optional<FireStation> getOneFireStationById(Long id) {
	 * Optional<FireStation> fireStationFoundById = Optional
	 * .ofNullable(fireStationRepository.findById(id).orElseThrow(() -> new
	 * NullPointerException( " an error has occured,this firestation " + id +
	 * " doesn't exist, try again "))); return fireStationFoundById; }
	 */

	/*
	 * public FireStation saveFireStation(@Valid FireStation fireStation) { return
	 * fireStationRepository.save(fireStation); }
	 * 
	 * public FireStation updateOneFireStationById(FireStation fireStation, Long id)
	 * { return fireStationRepository.save(fireStation); }
	 * 
	 * public void deleteByStationNumberFireStation( String stationNumber ) {
	 * fireStationRepository.deleteByStationNumberFireStation( stationNumber); /*
	 * List<String> listOfFireStation = new ArrayList<String>();
	 * listOfFireStation.add(fireStation.getStationNumber().toString());
	 * listOfFireStation.add(fireStation.getAddress()); listOfFireStation.remove(0);
	 * return listOfFireStation; //fireStationRepository.deleteById(id);
	 */
	/*
	 * }
	 * 
	 * public List<FireStation> saveFireStation2(FireStation fireStation,
	 * List<FireStation> fireStations ) { // TODO Auto-generated method stub
	 * 
	 * FireStation fireStationCreated =fireStationRepository.save(fireStation);
	 * fireStations.add(fireStationCreated); return fireStations; }
	 */

}
