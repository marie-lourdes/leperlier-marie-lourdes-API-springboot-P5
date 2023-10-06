package com.safetynet.api.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.api.model.FireStation;
import com.safetynet.api.service.dataservice.FireStationService;

import jakarta.validation.Valid;

@RestController
public class FireStationController {
	@Autowired
	private FireStationService fireStationService;
	
	private List<FireStation>	fireStations;
	@PostMapping("/firestation/")
	public ResponseEntity<List<FireStation>> createFireStation(@Valid @RequestBody FireStation fireStation) throws IOException {
		System.out.println(fireStation);
		fireStations = new LinkedList<FireStation>();
		fireStations =  fireStationService.getFireStationsFromFile();
		fireStations .add(fireStation);
		fireStationService.saveFireStation(fireStation);
		return ResponseEntity.status(HttpStatus.CREATED).body(fireStations);
	}

	// -----------------requete a partir du fichier json-------------
	@GetMapping("/firestation/")
	public @ResponseBody List<FireStation> getAllFireStationsFromFile() throws FileNotFoundException {
				fireStations = new LinkedList<FireStation>();

		try {
				fireStations = fireStationService.getFireStationsFromFile();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fireStations;
	}

//----firestation get by id BDD------
	/*@GetMapping("/firestation/{id}")
	public Optional<FireStation> getOneFireStation(@PathVariable Long id) {
		return fireStationService.getOneFireStationById(id);
	}*/
		
//----firestation get by stationNumber from file json------
	@GetMapping("/firestation")
	public  List<Optional<FireStation>> getFireStationsByNumber(@RequestParam String stationNumber){
		return fireStationService.getFireStationsByNumber(stationNumber);
	}

/*
	@PutMapping("/firestation/{id}")
	public ResponseEntity<Optional<FireStation>> updateOneFireStationById(@PathVariable Long id,
			@Valid @RequestBody FireStation fireStation) {
		Optional<FireStation> fireStationFoundById = fireStationService.getOneFireStationById(id);

		if (id.toString().equals(fireStationFoundById.get().getId().toString())) {
			fireStationFoundById.get().setStationNumber(fireStation.getStationNumber());
			fireStationFoundById.get().setAddress(fireStation.getAddress());

			fireStationService.saveFireStation(fireStationFoundById.get());
			System.out.println(fireStationFoundById);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(fireStationFoundById);
	}*/

	@DeleteMapping("/firestation/{id}")
	public ResponseEntity<Long> deleteOneFireStationById(@PathVariable String id) throws IOException {
		List<Optional<FireStation>> fireStationFoundById = fireStationService.getFireStationsByNumber(id);
		Iterator<Optional<FireStation>>  iteratorFireStation = fireStationFoundById .listIterator();
		 while( iteratorFireStation.hasNext()) {
			 Optional<FireStation>personItr = iteratorFireStation.next();
			
		if (id.toString().equals(personItr.get().getId().toString())) {
			fireStationService.deleteByStationNumberFireStation( id);
			//fireStationService.saveFireStation(fireStations);
			 return  new ResponseEntity<Long>(HttpStatus.NO_CONTENT);
		}
		
	}
		 
		 return  new ResponseEntity<Long>(HttpStatus.BAD_REQUEST);
		 
/*	@DeleteMapping("/firestation")
	public ResponseEntity<Long> deleteStationNumberOfFireStation(@RequestParam String stationNumber) {	
		 List<Optional<FireStation>>fireStationFoundByNumber =fireStationService.getFireStationsByNumber(stationNumber);
			Iterator<Optional<FireStation>>  iteratorFireStation = fireStationFoundByNumber .listIterator();
		 while( iteratorFireStation.hasNext()) {
			 Optional<FireStation>personItr = iteratorFireStation.next();
			 if (stationNumber.toString().equals(personItr.get().getStationNumber().toString())){
				 try {
					System.out.println("list fire stations AVANT suppression"+fireStationService.getFireStationsFromFile());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 fireStationService.deleteByStationNumberFireStation(stationNumber);
					//fireStationService.saveFireStation(fireStationFoundById.get());
				 try {
					System.out.println("list fire stations APRES suppression"+fireStationService.getFireStationsFromFile());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
			
		 }
		 return new ResponseEntity<Long>(HttpStatus.NO_CONTENT);*/
		/*FireStation fireStationWithStationNumberRemoved = new FireStation();
		List<Optional<FireStation>> personFoundByStationNumber = fireStationService.getFireStationsByNumber( stationNumber); 
		Iterator<Optional<FireStation>>  iteratorFireStation = personFoundByStationNumber.listIterator();
		 while( iteratorFireStation.hasNext()) {
			 Optional<FireStation>personItr = iteratorFireStation.next();
			 if (stationNumber.toString().equals(personItr.get().getStationNumber().toString()){
				 fireStationService.deleteByStationNumberFireStation(personItr );
			 }
		 }*/
	}
		
			/*
			 * fireStationWithStationNumberRemoved = new
			 * FireStation(fireStationFoundById.get().getId(),
			 * fireStationFoundById.get().getAddress());
			 */
		
	/*fireStationWithStationNumberRemoved = FireStationFactory
					.makeFireStation(FireStationType.STATIONNUMBER_REMOVED);
			fireStationWithStationNumberRemoved.setId(fireStationFoundById.get().getId());
			fireStationWithStationNumberRemoved.setAddress(fireStationFoundById.get().getAddress());

			fireStationService.saveFireStation(fireStationWithStationNumberRemoved);
			System.out.println(fireStationWithStationNumberRemoved);

		}
		return new ResponseEntity<Long>(HttpStatus.NO_CONTENT);
	}*/

	/*@DeleteMapping("/firestation/{id}/delete/address")
	public ResponseEntity<Long> deleteAddressOfFireStation(@PathVariable Long id) {
		Optional<FireStation> fireStationFoundById = fireStationService.getOneFireStationById(id);
		
		FireStation fireStationWithAddressRemoved = new FireStation();

		if (id.toString().equals(fireStationFoundById.get().getId().toString())) {
			fireStationWithAddressRemoved = FireStationFactory.makeFireStation(FireStationType.STATIONNUMBER_REMOVED);
			fireStationWithAddressRemoved.setId(fireStationFoundById.get().getId());
			fireStationWithAddressRemoved.setStationNumber(fireStationFoundById.get().getStationNumber());
			/*
			 * new FireStation(fireStationFoundById.get().getId(),
			 * fireStationFoundById.get().getStationNumber());
			 */
		/*	fireStationService.saveFireStation(fireStationWithAddressRemoved);
			System.out.println(fireStationWithAddressRemoved);
		}

		return new ResponseEntity<Long>(HttpStatus.NO_CONTENT);
	}*/

}

