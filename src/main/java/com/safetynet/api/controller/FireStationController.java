package com.safetynet.api.controller;

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
	public FireStation createFireStation( @Valid @RequestBody  FireStation fireStationCreated) {
		FireStation fireStation = new FireStation();
		fireStation.setStationNumber(fireStationCreated.getStationNumber());
		fireStation.setAddress(fireStationCreated.getAddress());
		return	fireStationService.saveFireStation(fireStation);
	}
	
	@GetMapping("/firestation")
	public @ResponseBody List<FireStation> getAllFireStations() {
		List<FireStation> allFireStations =null;
		try {
			 allFireStations  = fireStationService. getAllFireStations();
		}catch (NullPointerException  e) {
			e.printStackTrace(); 
		}catch (Exception e) {
			e.printStackTrace();
		}
		return  allFireStations;
	}
	
	@GetMapping("/firestation/{id}")
	public Optional<FireStation> getOneFireStation(@PathVariable Long id) {
		Optional<FireStation> fireStationFoundById  = Optional.ofNullable(fireStationService.getOneFireStationById(id).orElseThrow(
				() -> new NullPointerException(" an error has occured,this firestation" + id + "doesn't exist, try again ")));
		return fireStationFoundById;
	}
	
	@PutMapping("/firestation/{id}")
	public Optional<FireStation> updateOneFireStationById(@PathVariable Long id,@Valid @RequestBody FireStation fireStationModified) {	
		Optional<FireStation> fireStationFoundById = this.getOneFireStation(id);
		if (id == fireStationFoundById.get().getId()) {
			if(fireStationModified.getStationNumber() != null)
			fireStationFoundById.get().setStationNumber(fireStationModified.getStationNumber());
			if(fireStationModified.getAddress() != null)
			fireStationFoundById.get().setAddress(fireStationModified.getAddress());		
			fireStationService.saveFireStation(fireStationFoundById.get());
		}
		return fireStationFoundById;
	}
	
	@DeleteMapping("/firestation/{id}")
	public void deleteOneFireStationById(@PathVariable Long id) {
		Optional<FireStation> fireStationFoundById = this.getOneFireStation(id);
		
	/*	FireStation elementTodelete = null;*/
		if (id == fireStationFoundById.get().getId()) {
			fireStationService.deleteStationNumberFireStation(fireStationFoundById.get(), id);
		/* fireStationService.deleteStationNumberFireStation(fireStationFoundById.get());
		fireStationService.saveFireStation(fireStationFoundById.get());*/
		
		}
				//return new ResponseEntity<Long>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("/firestation/{id}/delete/station-number")
	public ResponseEntity<Long>deleteStationNumberOfFireStation(@PathVariable Long id) {
		Optional<FireStation> fireStationFoundById = this.getOneFireStation(id);
		FireStation  fireStationWithStationNumberRemoved = null;
		if (id == fireStationFoundById.get().getId()) {
			// fireStationFoundById.get().setStationNumber(null);
			fireStationWithStationNumberRemoved = new FireStation(fireStationFoundById.get().getId(),fireStationFoundById.get().getAddress());
			fireStationService.saveFireStation( fireStationWithStationNumberRemoved );
			 
		}
		return new ResponseEntity<Long>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("/firestation/{id}/delete/address")
	public ResponseEntity<Long>deleteAddressOfFireStation(@PathVariable Long id) {
		Optional<FireStation> fireStationFoundById = this.getOneFireStation(id);
		FireStation  fireStationWithAddressRemoved  = null;
		if (id == fireStationFoundById.get().getId()) {
			// fireStationFoundById.get().setStationNumber(null);
		fireStationWithAddressRemoved  = new FireStation(fireStationFoundById.get().getId(),fireStationFoundById.get().getStationNumber());		
		fireStationService.saveFireStation(fireStationWithAddressRemoved );
		}
		 
		 return new ResponseEntity<Long>(HttpStatus.NO_CONTENT);
	}
}
