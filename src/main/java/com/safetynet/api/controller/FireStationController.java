package com.safetynet.api.controller;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

	@PostMapping("/firestation")
	public ResponseEntity<FireStation> createFireStation(@Valid @RequestBody FireStation fireStation) {
		System.out.println(fireStation);
		fireStationService.saveFireStation(fireStation);
		return ResponseEntity.status(HttpStatus.CREATED).body(fireStation);
	}

	// -----------------requete a partir du fichier json-------------
	@GetMapping("/firestation/")
	public @ResponseBody List<FireStation> getAllFireStationsFromFile() throws FileNotFoundException {
		List<FireStation> fireStations = new LinkedList<FireStation>();

		try {
			fireStations = fireStationService.getFireStationsFromFile();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fireStations;
	}

//----------------requete a partir de la base de données--------------
	/*
	 * @GetMapping("/firestation") public @ResponseBody List<FireStation>
	 * getAllFireStations() { List<FireStation> allFireStations = new
	 * ArrayList<FireStation>();
	 * 
	 * try { allFireStations = fireStationService.getAllFireStations(); } catch
	 * (NullPointerException e) { e.printStackTrace(); } catch (Exception e) {
	 * e.printStackTrace(); } return allFireStations; }
	 */

//----firestation get by id BDD------
	/*@GetMapping("/firestation/{id}")
	public Optional<FireStation> getOneFireStation(@PathVariable Long id) {
		return fireStationService.getOneFireStationById(id);
	}*/
	
//----firestation get by stationNumber from file json------
	@GetMapping("/firestation")
	public  List<Optional<FireStation>> getOneFireStationByNumber(@RequestParam String stationNumber){
		return fireStationService.getOneFireStationByNumber(stationNumber);
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
	}

	@DeleteMapping("/firestation/{id}")
	public ResponseEntity<Long> deleteOneFireStationById(@PathVariable Long id) {
		Optional<FireStation> fireStationFoundById = fireStationService.getOneFireStationById(id);

		if (id.toString().equals(fireStationFoundById.get().getId().toString())) {
			fireStationService.deleteStationNumberFireStation(fireStationFoundById.get(), id);
		}
		return new ResponseEntity<Long>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/firestation/{id}/delete/station-number")
	public ResponseEntity<Long> deleteStationNumberOfFireStation(@PathVariable Long id) {
		Optional<FireStation> fireStationFoundById = fireStationService.getOneFireStationById(id);
		FireStation fireStationWithStationNumberRemoved = new FireStation();

		if (id.toString().equals(fireStationFoundById.get().getId().toString())) {
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
	}

	@DeleteMapping("/firestation/{id}/delete/address")
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
