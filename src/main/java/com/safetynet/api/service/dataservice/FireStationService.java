package com.safetynet.api.service.dataservice;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.safetynet.api.model.FireStation;
import com.safetynet.api.utils.Constants;
import com.safetynet.api.utils.ICheckingDuplicatedObject;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FireStationService implements ICheckingDuplicatedObject<FireStation> {
	private static final Logger log = LogManager.getLogger(FireStationService.class);

	private final List<FireStation> fireStations = new ArrayList<>();

	public FireStation addFireStation(FireStation fireStation) {
		log.debug("Adding FireStation: {} {}", fireStation.getStationNumber(), fireStation.getAddress());

		boolean isObjectDuplicated = this.isFireStationDuplicatedByAddress(fireStations, fireStation);

		if (isObjectDuplicated) {
			log.error("Failed to add this firestation, this firestation already exist {} ", fireStation);
			return null;
		} else {
			this.generateId(fireStation);
			fireStations.add(fireStation);
			log.debug("FireStation added successfully: {}", fireStation);
		}

		return fireStation;
	}

	public FireStation addStationNumberOfFireStationWithExistingAddress(String address, FireStation fireStation) {
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
			log.debug("Firestation created successfully with new station number  and existing address: {}",
					fireStation);
		} catch (NullPointerException e) {
			throw new NullPointerException("Failed to add  firestation with new station number , the address: "
					+ address + " " + Constants.NOT_FOUND);
		}

		return fireStation;
	}

	public FireStation addAddressOfFireStationWithExistingStationNumber(String stationNumber, FireStation fireStation)
			throws NullPointerException, IllegalArgumentException {
		log.debug("Adding a firestation with new address and existing station number: {}", stationNumber);

		boolean isObjectDuplicated = this.isFireStationDuplicatedByAddress(fireStations, fireStation);

		if (isObjectDuplicated) {
			throw new IllegalArgumentException(
					"Failed to add this firestation, this firestation already exist" + fireStation);
		}

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
			log.debug("Firestation created successfully with new address  and existing station number: {}",
					fireStation);
		} catch (NullPointerException e) {
			throw new NullPointerException("Failed to add  firestation with new address , the station number: "
					+ stationNumber + " " + Constants.NOT_FOUND);
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
								+ address + " " + Constants.NOT_FOUND));

		log.debug("FireStation updated successfully for address: {}", existingFireStationUpdated);
		return existingFireStationUpdated;
	}

	public boolean deleteFireStationByStationNumber(String stationNumber) throws NullPointerException {
		log.debug("Deleting firestation for station number{}", stationNumber);

		boolean result = false;
		result = fireStations.removeIf(fireStation -> fireStation.getStationNumber().equals(stationNumber));

		if (!result) {
			log.error("Failed to delete firestation for station number {}", stationNumber);
		} else {
			log.debug("Firestation deleted  successfully for station number {}", stationNumber);
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
			log.debug("Firestation deleted successfully for address  {}", address);
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
			log.debug("Firestation(s) retrieved  successfully for  station number {}", stationNumber);
		}

		log.debug("List of firestations retrieved by station number successfully : {}",
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
			log.debug("Firestation(s) retrieved  successfully for  address {}", address);
		}

		log.debug("List of firestations retrieved by address successfully : {}", fireStationsFoundByAddress);
		return fireStationsFoundByAddress;
	}

	public List<FireStation> getAllFireStations() throws NullPointerException {
		log.debug("Retrieving all firestations");

		if (fireStations.isEmpty()) {
			log.error("Failed to retrieve all  firestations ");
			throw new NullPointerException("None firestation registered!");
		} else {
			log.debug("All firestations retrieved successfully: {}", fireStations);
		}

		return fireStations;
	}

	public void generateId(FireStation fireStationCreated) {
		log.debug("Generating id for firestation created  : {}", fireStationCreated);

		String[] addressSplit = fireStationCreated.getAddress().split(" ", -1);
		String numberOfAddress = addressSplit[0];
		final String ID = numberOfAddress + "-" + fireStationCreated.getStationNumber() + "-"
				+ Math.round(Math.random() * 100 + 1);

		fireStationCreated.setId(ID);
		log.debug("Id : {} generated successfully for firestation created  : {}", ID, fireStationCreated);
	}

	public boolean isFireStationDuplicatedByAddress(List<FireStation> fireStations, FireStation fireStation) {
		return this.isObjectDuplicated(fireStations, fireStation);
	}

	@Override
	public boolean isObjectDuplicated(List<FireStation> fireStations, FireStation fireStation) {
		boolean isObjectDuplicated = false;
		for (FireStation fireStationExisting : fireStations) {
			if (fireStationExisting.getAddress().equals(fireStation.getAddress())) {
				isObjectDuplicated = true;
			}
		}
		return isObjectDuplicated;
	}
}
