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
		fireStation.setId(fireStation.getStationNumber());
		fireStations.add(fireStation);
		return fireStation;
	}

//ajouter illagal state argumnt pour le body vide passé en parametre sans entrée et envoyer un code erreur de non creation de donnée ,
//car les annotation permette de renvoyer erreur 400
	public FireStation addStationNumberOfExistingFireStation(FireStation fireStation, String address) throws NullPointerException  {
		log.debug("Replacing a firestation with new station number based on address existing firestation: {}", address);

		FireStation fireStationByAddress = new FireStation();
		try {
			fireStationByAddress = getOneFireStationByAddress(address);
			fireStations.removeIf(
					fireStationByAddressToRemove -> fireStationByAddressToRemove.getAddress().equals(address));

			fireStation.setId(fireStation.getStationNumber());
			fireStation.setAddress(fireStationByAddress.getAddress());
			fireStationByAddress.setStationNumber(fireStation.getStationNumber());
			fireStationByAddress.setId(fireStation.getId());
			fireStation = fireStationByAddress;
			fireStations.add(fireStation);

			log.info("Firestation replaced with new station number and  added successfully: {}", fireStation);
			return fireStation;
		}catch (NullPointerException e) {
			throw new NullPointerException(
					"Failed to replace firestation with new station number , the address: " + address + " not found");
		}
	}

	public FireStation addAddressOfExistingFireStation(FireStation fireStation, String stationNumber)
			throws NullPointerException {
		log.debug("Replacing a firestation with new address based onstation number existing firestation: {}",
				stationNumber);
		FireStation createdFireStation = new FireStation();

		try {
			List<FireStation> fireStationsByStationNumber = getFireStationsById(stationNumber);
			fireStations.removeIf(fireStationByAddressToRemove -> fireStationByAddressToRemove.getStationNumber()
					.equals(stationNumber));
			createdFireStation = fireStationsByStationNumber.stream()
					.filter(fireStationByStationNumber -> fireStationByStationNumber.getId().equals(stationNumber))
					.findFirst().map(existingFireStation -> {
						existingFireStation.setId(fireStation.getStationNumber());
						existingFireStation.setAddress(fireStation.getAddress());
						return existingFireStation;
					}).orElse(null);
			fireStations.add(createdFireStation);

		} catch (NullPointerException e) {
			throw new NullPointerException("Failed to replace firestation with new address , the station number: "
					+ stationNumber + " not found");
		}

		/*
		 * }catch(Exception e) { e.printStackTrace(); throw new
		 * NoSuchElementException("aucun element trouvé");
		 */
		log.info("Firestation replaced with new address  and  added successfully: {}", fireStation);
		return createdFireStation;
	}

	// update only station number not address of firestation
	public FireStation updateFireStation(String id, FireStation updatedFireStation) throws NullPointerException {
		log.debug("Updating fireStation for id station number: {}", id);

		FireStation existingFireStationUpdated = new FireStation();
		existingFireStationUpdated = fireStations.stream().filter(fireStation -> fireStation.getId().equals(id))
				.findFirst().map(existingFireStation -> {
					existingFireStation.setStationNumber(updatedFireStation.getStationNumber());
					existingFireStation.setId(updatedFireStation.getStationNumber());
					return existingFireStation;
				}).orElseThrow(() -> new NullPointerException(
						"Failed to update firestation,the station number  :" + id + " not found"));

		log.info("FireStation updated successfully for station number: {}", updatedFireStation);
		return existingFireStationUpdated;
	}

	public boolean deleteFireStationById(String id) throws NullPointerException {
		log.debug("Deleting firestation for id station number{}", id);

		boolean result = false;
		result = fireStations.removeIf(fireStation -> fireStation.getId().equals(id));

		if (!result) {
			log.error("Failed to delete firestation for id station number {}", id);
			throw new NullPointerException(" Firestation with this station number: " + id + "  to delete not found");
		} else {
			log.info("Firestation deleted  successfully for id station number {}", id);
		}

		return result;
	}

	public boolean deleteOneFireStationByAddress(String address) throws NullPointerException {
		log.debug("Deleting firestation for  address {}", address);

		boolean result = false;
		result = fireStations.removeIf(fireStation -> fireStation.getAddress().equals(address));
		if (!result) {
			log.error("Failed to delete firestation for address {}", address);
			throw new NullPointerException(" Firestation with this address: " + address + "  to delete not found");
		} else {
			log.info("Firestation deleted successfully for address  {}", address);
		}
		return result;
	}

	public List<FireStation> getFireStationsById(String id) throws NullPointerException {
		log.debug("Retrieving  firestation(s)  for id station number {}", id);
		List<FireStation> fireStationsFoundById = new ArrayList<>();
		Iterator<FireStation> itrFireStations = fireStations.listIterator();
		while (itrFireStations.hasNext()) {
			FireStation itrFireStation = itrFireStations.next();
			if (itrFireStation.getId().equals(id)) {
				fireStationsFoundById.add(itrFireStation);		
			}
		}

		if (fireStationsFoundById.isEmpty()) {
			log.error("Failed to retrieve firestation(s) by id station number for {}", id);
			throw new NullPointerException("Firestation(s) not found by id station number: " + id);
		}else {
			log.info("Firestation(s) retrieved  successfully for  id station number {}", id);
		}
		
		log.info("List of firestations retrieved by station number successfully : {}", fireStationsFoundById);
		return fireStationsFoundById;

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
		}else {
			log.info("All firestations retrieved successfully: {}", fireStations);
		}
		
		return fireStations;
	}
}
