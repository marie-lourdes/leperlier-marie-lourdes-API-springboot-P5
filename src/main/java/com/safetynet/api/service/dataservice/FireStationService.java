package com.safetynet.api.service.dataservice;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.safetynet.api.model.FireStation;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FireStationService {
	private static final Logger log = LogManager.getLogger(FireStationService.class);
	private List<FireStation> fireStations = new ArrayList<>();

	public FireStation addFireStation(FireStation fireStation) {
		log.debug("Adding FireStation: {}", fireStation.getStationNumber() + " " + fireStation.getAddress());

		this.generateId(fireStation);
		fireStations.add(fireStation);

		log.info("FireStation added successfully: {}", fireStation);
		return fireStation;
	}

//ajouter illegal state argumnt pour le body vide passé en parametre sans entrée et envoyer un code erreur de non creation de donnée ,
//car les annotation permette de renvoyer erreur 400
	public FireStation addStationNumberOfExistingFireStation(FireStation fireStation, String address)
			throws NullPointerException {
		log.debug("Replacing a firestation with new station number based on address existing firestation: {}", address);

		FireStation fireStationByAddress = new FireStation();

		fireStationByAddress = getOneFireStationByAddress(address);
		fireStations
				.removeIf(fireStationByAddressToRemove -> fireStationByAddressToRemove.getAddress().equals(address));

		if (fireStation == null) {
			throw new NullPointerException(
					"Failed to replace firestation with new station number , the address: " + address + " not found");
		}

		this.generateId(fireStation);
		fireStation.setAddress(fireStationByAddress.getAddress());
		fireStationByAddress.setStationNumber(fireStation.getStationNumber());
		fireStationByAddress.setId(fireStation.getId());
		fireStation = fireStationByAddress;

		fireStations.add(fireStation);

		log.info("Firestation replaced with new station number and  added successfully: {}", fireStation);

		return fireStation;
	}

	public FireStation addAddressOfExistingFireStation(FireStation fireStation, String stationNumber)
			throws NullPointerException {
		log.debug("Replacing a firestation with new address based onstation number existing firestation: {}",
				stationNumber);
		FireStation createdFireStation = new FireStation();

		List<FireStation> fireStationsByStationNumber = getFireStationsByStationNumber(stationNumber);
		fireStations.removeIf(
				fireStationByAddressToRemove -> fireStationByAddressToRemove.getStationNumber().equals(stationNumber));
		createdFireStation = fireStationsByStationNumber.stream().filter(
				fireStationByStationNumber -> fireStationByStationNumber.getId().toString().equals(stationNumber))
				.findFirst().map(existingFireStation -> {
					this.generateId(existingFireStation);
					existingFireStation.setAddress(fireStation.getAddress());
					return existingFireStation;
				}).orElse(null);
		if (createdFireStation == null) {
			throw new NullPointerException("Failed to replace firestation with new address , the station number: "
					+ stationNumber + " not found");
		}
		fireStations.add(createdFireStation);

		log.info("Firestation replaced with new address  and  added successfully: {}", fireStation);
		return createdFireStation;
	}

	// update only station number not address of firestation
	public FireStation updateFireStation(String address, FireStation updatedFireStation) throws NullPointerException {
		log.debug("Updating station number of fireStation  for address : {}", address);

		FireStation existingFireStationUpdated = new FireStation();
		existingFireStationUpdated = fireStations.stream()
				.filter(fireStation -> fireStation.getAddress().equals(address)).findFirst()
				.map(existingFireStation -> {
					existingFireStation.setStationNumber(updatedFireStation.getStationNumber());
					return existingFireStation;
				}).orElseThrow(() -> new NullPointerException(
						"Failed to update station number of fireStation, the address :" + address + " not found"));

		log.info("FireStation updated successfully for address: {}", updatedFireStation);
		return existingFireStationUpdated;
	}

	public boolean deleteFireStationByStationNumber(String stationNumber) throws NullPointerException {
		log.debug("Deleting firestation for station number{}", stationNumber);

		boolean result = false;
		result = fireStations.removeIf(fireStation -> fireStation.getId().equals(stationNumber));

		if (!result) {
			log.error("Failed to delete firestation for station number {}", stationNumber);
		} else {
			log.info("Firestation deleted  successfully for station number {}", stationNumber);
		}

		return result;
	}

	public boolean deleteOneFireStationByAddress(String address) throws NullPointerException {
		log.debug("Deleting firestation for  address {}", address);

		boolean result = false;
		result = fireStations.removeIf(fireStation -> fireStation.getAddress().equals(address));
		if (!result) {
			log.error("Failed to delete firestation for address {}", address);
		} else {
			log.info("Firestation deleted successfully for address  {}", address);
		}

		return result;
	}

	public List<FireStation> getFireStationsByStationNumber(String stationNumber) throws NullPointerException {
		log.debug("Retrieving  firestation(s)  for  station number {}", stationNumber);

		List<FireStation> fireStationsFoundByStationNumber = new ArrayList<>();
		Iterator<FireStation> itrFireStations = fireStations.listIterator();
		while (itrFireStations.hasNext()) {
			FireStation itrFireStation = itrFireStations.next();
			if (itrFireStation.getStationNumber().equals(stationNumber)) {
				fireStationsFoundByStationNumber.add(itrFireStation);
			}
		}

		if (fireStationsFoundByStationNumber.isEmpty()) {
			log.error("Failed to retrieve firestation(s) by  station number for {}", stationNumber);
			throw new NullPointerException("Firestation(s) not found by  station number: " + stationNumber);
		} else {
			log.info("Firestation(s) retrieved  successfully for  station number {}", stationNumber);
		}

		log.info("List of firestations retrieved by station number successfully : {}",
				fireStationsFoundByStationNumber);
		return fireStationsFoundByStationNumber;

	}

	public FireStation getOneFireStationByAddress(String address) throws NullPointerException {
		log.debug("Retrieving  firestation  for address {}", address);

		FireStation fireStationFoundByAddress = new FireStation();
		fireStationFoundByAddress = fireStations.stream()
				.filter(fireStation -> fireStation.getAddress().equals(address)).findAny().map(existingFireStation -> {
					return existingFireStation;
				}).orElseThrow(() -> new NullPointerException("Firestation not found by address: " + address));

		log.info("Firestation retrieved successfully for address: {}", address);
		return fireStationFoundByAddress;
	}

	public List<FireStation> getAllFireStations() throws NullPointerException {
		log.debug("Retrieving all firestations");

		if (fireStations.isEmpty()) {
			log.error("Failed to retrieve all  firestations ");
			throw new NullPointerException("None firestation registered!");
		} else {
			log.info("All firestations retrieved successfully: {}", fireStations);
		}

		return fireStations;
	}

	public void generateId(FireStation fireStationCreated) {
		String[] addressSplit = fireStationCreated.getAddress().split(" ", -1);
		String numberOfAddress = addressSplit[0];
		fireStationCreated.setId(numberOfAddress + "-" + fireStationCreated.getStationNumber() + "-"
				+ Math.round(Math.random() * 100 + 1));
	}
}
