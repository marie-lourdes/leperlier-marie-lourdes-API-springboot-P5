package com.safetynet.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.api.model.FireStation;
import com.safetynet.api.service.dataservice.FireStationService;

import jakarta.validation.Valid;

@RestController
public class FireStationController {
	@Autowired
	FireStationService fireStationService;

	@PostMapping("/firestation")
	public ResponseEntity<FireStation> createFireStation(@Valid @RequestBody FireStation fireStation) {
		fireStationService.saveFireStation(fireStation);
		return ResponseEntity.status(HttpStatus.CREATED).body(fireStation);
	}

	@GetMapping("/firestation")
	public @ResponseBody List<FireStation> getAllFireStations() {
		List<FireStation> allFireStations = new ArrayList<FireStation>();

		try {
			allFireStations = fireStationService.getAllFireStations();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allFireStations;
	}

	@GetMapping("/firestation/{id}")
	public Optional<FireStation> getOneFireStation(@PathVariable Long id) {
		Optional<FireStation> fireStationFoundById = Optional
				.ofNullable(fireStationService.getOneFireStationById(id).orElseThrow(() -> new NullPointerException(
						" an error has occured,this firestation" + id + "doesn't exist, try again ")));
		return fireStationFoundById;
	}

	@PutMapping("/firestation/{id}")
	public ResponseEntity<Optional<FireStation>> updateOneFireStationById(@PathVariable Long id,
			@Valid @RequestBody FireStation fireStation) {
		Optional<FireStation> fireStationFoundById = fireStationService.getOneFireStationById(id);

		if (id.toString().equals(fireStationFoundById.get().getId().toString()) ) {
				fireStationFoundById.get().setStationNumber(fireStation.getStationNumber());
				fireStationFoundById.get().setAddress(fireStation.getAddress());
			fireStationService.saveFireStation(fireStationFoundById.get());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(fireStationFoundById);
	}

	@DeleteMapping("/firestation/{id}")
	public ResponseEntity<Long> deleteOneFireStationById(@PathVariable Long id) {
		Optional<FireStation> fireStationFoundById = this.getOneFireStation(id);

		if (id == fireStationFoundById.get().getId()) {
			fireStationService.deleteStationNumberFireStation(fireStationFoundById.get(), id);
		}
		return new ResponseEntity<Long>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/firestation/{id}/delete/station-number")
	public ResponseEntity<Long> deleteStationNumberOfFireStation(@PathVariable Long id) {
		Optional<FireStation> fireStationFoundById = this.getOneFireStation(id);
		FireStation fireStationWithStationNumberRemoved = new FireStation();

		if (id == fireStationFoundById.get().getId()) {
			fireStationWithStationNumberRemoved = new FireStation(fireStationFoundById.get().getId(),
					fireStationFoundById.get().getAddress());
			
			System.out.println(fireStationWithStationNumberRemoved);
			fireStationService.saveFireStation(fireStationWithStationNumberRemoved);

		}
		return new ResponseEntity<Long>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/firestation/{id}/delete/address")
	public ResponseEntity<Long> deleteAddressOfFireStation(@PathVariable Long id) {
		Optional<FireStation> fireStationFoundById = this.getOneFireStation(id);
		FireStation fireStationWithAddressRemoved = new FireStation();
		if (id == fireStationFoundById.get().getId()) {
			fireStationWithAddressRemoved = new FireStation(fireStationFoundById.get().getId(),
					fireStationFoundById.get().getStationNumber());
			
			System.out.println(fireStationWithAddressRemoved);
			fireStationService.saveFireStation(fireStationWithAddressRemoved);
		}

		return new ResponseEntity<Long>(HttpStatus.NO_CONTENT);
	}
}
