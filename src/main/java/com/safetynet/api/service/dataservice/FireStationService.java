package com.safetynet.api.service.dataservice;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.safetynet.api.model.FireStation;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FireStationService {
	private List<FireStation> fireStations = new ArrayList<>();

	public FireStation addFireStation(FireStation fireStation) {
		fireStation.setId(fireStation.getStationNumber());
		fireStations.add(fireStation);
		return fireStation;
	}

	public FireStation addStationNumberOfExistingFireStation(FireStation fireStation, String address) {
		Optional<FireStation> fireStationByAddress = getOneFireStationByAddress(address);
		fireStations
				.removeIf(fireStationByAddressToRemove -> fireStationByAddressToRemove.getAddress().equals(address));

		fireStation.setId(fireStation.getStationNumber());
		fireStation.setAddress(fireStationByAddress.get().getAddress());
		fireStationByAddress.get().setStationNumber(fireStation.getStationNumber());
		fireStationByAddress.get().setId(fireStation.getId());

		// fireStation.setId( fireStationByAddress.get().getId());
		fireStation = fireStationByAddress.get();
		if (fireStation == null) {
			return null;
		}
		fireStations.add(fireStation);
		return fireStation;
	}

	public FireStation addAddressOfExistingFireStation(FireStation fireStation, String stationNumber) {
		List<FireStation> fireStationsByStationNumber = getFireStationsById(stationNumber);
		// try {
		fireStations.removeIf(
				fireStationByAddressToRemove -> fireStationByAddressToRemove.getStationNumber().equals(stationNumber));

		/*
		 * }catch(Exception e) { e.printStackTrace(); throw new
		 * NoSuchElementException("aucun element trouvé");
		 * 
		 * }
		 */

		FireStation createdFireStation = fireStationsByStationNumber.stream()
				.filter(fireStationByStationNumber -> fireStationByStationNumber.getId().equals(stationNumber))
				.findFirst().map(existingFireStation -> {
					existingFireStation.setId(fireStation.getStationNumber());
					existingFireStation.setAddress(fireStation.getAddress());
					// existingFireStation.setAddress(updatedFireStation.getAddress());
					return existingFireStation;
				}).orElse(null);

		fireStations.add(createdFireStation);
		return createdFireStation;
	}

	// modification uniquement de la station number et non l'addresse
	public FireStation updateFireStation(String id, FireStation updatedFireStation) {
		return fireStations.stream().filter(fireStation -> fireStation.getId().equals(id)).findFirst()
				.map(existingFireStation -> {
					existingFireStation.setStationNumber(updatedFireStation.getStationNumber());
					existingFireStation.setId(updatedFireStation.getStationNumber());
					return existingFireStation;
				}).orElse(null);
	}

	public boolean deleteFireStationById(String id) {
		// log.debug("Deleting medical record for {} {}", firstName, lastName);
		boolean result = fireStations.removeIf(fireStation -> fireStation.getId().equals(id));
		if (result) {
			// log.info("firestation deleted by id with station number successfully for {}
			// {}", id);
		} else {
			// log.error("Failed to delete firestation for {} {}", id);
		}
		return result;
	}

	public boolean deleteOneFireStationByAddress(String address) {
		// log.debug("Deleting medical record for {} {}", firstName, lastName);
		boolean result = fireStations.removeIf(fireStation -> fireStation.getAddress().equals(address));
		if (result) {
			// log.info("firestation deleted by address with station number successfully for
			// {} {}", id);
		} else {
			// log.error("Failed to delete firestation for {} {}", id);
		}
		return result;
	}

	public List<FireStation> getFireStationsById(String id) {
		List<FireStation> fireStationsFoundById = new ArrayList<>();
		Iterator<FireStation> itrFireStations = fireStations.listIterator();
		while (itrFireStations.hasNext()) {
			FireStation itrFireStation = itrFireStations.next();
			if (itrFireStation.getId().equals(id)) {
				fireStationsFoundById.add(itrFireStation);
			}

			if (fireStationsFoundById.isEmpty()) {
				return null;
			}
		}
		System.out.println("fireStationsFoundById" + fireStationsFoundById);
		return fireStationsFoundById;

	}

	public Optional<FireStation> getOneFireStationByAddress(String address) {
		return fireStations.stream().filter(fireStation -> fireStation.getAddress().equals(address)).findAny()
				.map(existingFireStation -> {
					return existingFireStation;
				});
	}

	public List<FireStation> getAllFireStations() {
		if (fireStations.isEmpty()) {
			return null;
		}
		System.out.println("Retrieving all persons" + fireStations);
		return fireStations;
	}
}
