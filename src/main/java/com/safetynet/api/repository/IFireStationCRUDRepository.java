package com.safetynet.api.repository;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.safetynet.api.model.FireStation;

public interface IFireStationCRUDRepository  extends CrudRepository<FireStation, String>{
	default void deleteByStationNumberFireStation( String stationNumber) {
		FireStationREADONLYRepositoryImpl fireStationREADONLYRepositoryImpl = new FireStationREADONLYRepositoryImpl();
		//fireStationRepositoryFile.findByStationNumber(stationNumber);
	List<Optional<FireStation>>	fireStationToRemove =fireStationREADONLYRepositoryImpl.findByStationNumber(stationNumber);
	Iterator<Optional<FireStation>> iteratorFireStation = fireStationToRemove.listIterator();
	 while( iteratorFireStation.hasNext()) {
		 Optional<FireStation>personItr = iteratorFireStation.next();
		 if (stationNumber.toString().equals(personItr.get().getStationNumber().toString())){
			 iteratorFireStation.remove();
		 }
	 }
	}
	 	
}
