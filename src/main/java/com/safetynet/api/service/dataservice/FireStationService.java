package com.safetynet.api.service.dataservice;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.safetynet.api.model.FireStation;
import com.safetynet.api.utils.Constants;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FireStationService {
	private static final Logger log = LogManager.getLogger(FireStationService.class);
<<<<<<< Updated upstream
	
	private List<FireStation> fireStations = new ArrayList<>();
=======

	private final List<FireStation> fireStations = new ArrayList<>();
>>>>>>> Stashed changes

	public FireStation addFireStation(FireStation fireStation) {
		log.debug("Adding FireStation: {}", fireStation.getStationNumber() + " " + fireStation.getAddress());

		this.generateId(fireStation);
		fireStations.add(fireStation);

		log.info("FireStation added successfully: {}", fireStation);
		return fireStation;
	}

	public FireStation addStationNumberOfFireStationWithExistingAddress(String address, FireStation fireStation)
			throws NullPointerException {
		log.debug("Adding a firestation with new station number and existing address: {}", address);

		FireStation createdFireStation = new FireStation();
		try {
			List<FireStation> fireStationsByAddress = getFireStationsByAddress(address);

			createdFireStation = fireStationsByAddress.stream()
					.filter(fireStationByAddress -> fireStationByAddress.getAddress().equals(address)).findFirst()
					.map(existingFireStation -> {
						fireStation.setAddress(existingFireStation.getAddress());
						this.generateId(fireStation);
						return fireStation;
					}).orElse(null);

			fireStations.add(createdFireStation);
			log.info("Firestation created successfully with new station number  and existing address: {}", fireStation);
		} catch (NullPointerException e) {
			throw new NullPointerException("Failed to add  firestation with new station number , the address: "
					+ address + Constants.NOT_FOUND);
		}

		return fireStation;
	}

	public FireStation addAddressOfFireStationWithExistingStationNumber(String stationNumber, FireStation fireStation)
			throws NullPointerException {
		log.debug("Adding a firestation with new address and existing station number: {}", stationNumber);
		FireStation createdFireStation = new FireStation();
		try {
			List<FireStation> fireStationsByStationNumber = getFireStationsByStationNumber(stationNumber);

			createdFireStation = fireStationsByStationNumber.stream().filter(
					fireStationByStationNumber -> fireStationByStationNumber.getStationNumber().equals(stationNumber))
					.findFirst().map(existingFireStation -> {
						fireStation.setStationNumber(existingFireStation.getStationNumber());
						this.generateId(fireStation);
						return fireStation;
					}).orElse(null);

			fireStations.add(createdFireStation);
			log.info("Firestation created successfully with new address  and existing station number: {}", fireStation);
		} catch (NullPointerException e) {
			throw new NullPointerException("Failed to add  firestation with new address , the station number: "
					+ stationNumber + Constants.NOT_FOUND);
		}

		return createdFireStation;
	}

	// update only station number not address of firestation
	public FireStation updateFireStationByAddress(String address, FireStation updatedFireStation)
			throws NullPointerException {
		log.debug("Updating station number of fireStation  for address : {}", address);

		FireStation existingFireStationUpdated = new FireStation();
		existingFireStationUpdated = fireStations.stream()
				.filter(fireStation -> fireStation.getAddress().equals(address)).findFirst()
				.map(existingFireStation -> {
					existingFireStation.setStationNumber(updatedFireStation.getStationNumber());
					return existingFireStation;
				}).orElseThrow(
						() -> new NullPointerException("Failed to update station number of fireStation, the address :"
								+ address + Constants.NOT_FOUND));

		log.info("FireStation updated successfully for address: {}", updatedFireStation);
		return existingFireStationUpdated;
	}

	public boolean deleteFireStationByStationNumber(String stationNumber) throws NullPointerException {
		log.debug("Deleting firestation for station number{}", stationNumber);

		boolean result = false;
		result = fireStations.removeIf(fireStation -> fireStation.getStationNumber().equals(stationNumber));

		if (!result) {
			log.error("Failed to delete firestation for station number {}", stationNumber);
		} else {
			log.info("Firestation deleted  successfully for station number {}", stationNumber);
		}

		return result;
	}

	public boolean deleteFireStationByAddress(String address) throws NullPointerException {
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

	public List<FireStation> getFireStationsByAddress(String address) throws NullPointerException {
		log.debug("Retrieving  firestation  for address {}", address);

		List<FireStation> fireStationsFoundByAddress = new ArrayList<>();
		Iterator<FireStation> itrFireStations = fireStations.listIterator();
		while (itrFireStations.hasNext()) {
			FireStation itrFireStation = itrFireStations.next();
			if (itrFireStation.getAddress().equals(address)) {
				fireStationsFoundByAddress.add(itrFireStation);
			}
		}

		if (fireStationsFoundByAddress.isEmpty()) {
			log.error("Failed to retrieve firestation(s) by  address for {}", address);
			throw new NullPointerException("Firestation(s) not found by  address: " + address);
		} else {
			log.info("Firestation(s) retrieved  successfully for  address {}", address);
		}

		log.info("List of firestations retrieved by address successfully : {}", fireStationsFoundByAddress);
		return fireStationsFoundByAddress;
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
		log.debug("Generating id for firestation created  : {}", fireStationCreated);
		double random = Math.round(Math.random() * 100 + 1);
		String[] addressSplit = fireStationCreated.getAddress().split(" ", -1);
		String numberOfAddress = addressSplit[0];
		final String ID = numberOfAddress + "-" + fireStationCreated.getStationNumber() + "-" + random;

		fireStationCreated.setId(ID);
		log.debug("Id : {} generated successfully for firestation created  : {}", ID, fireStationCreated);
	}
}
