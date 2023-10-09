package com.safetynet.api.repository;

import java.io.IOException;
import java.util.List;

import com.safetynet.api.model.FireStation;

public interface IFireStationRepository extends IReadOnlyDatasRepository<FireStation,String> {
	@Override
	List<FireStation> findAll() throws IOException;
	
	/*	default void deleteByStationNumberFireStation( String stationNumber) {
		/*	FireStationREADONLYRepositoryImpl fireStationREADONLYRepositoryImpl = new FireStationREADONLYRepositoryImpl();
			//fireStationRepositoryFile.findByStationNumber(stationNumber);
		List<Optional<FireStation>>	fireStationToRemove =fireStationREADONLYRepositoryImpl.findByStationNumber(stationNumber);
		Iterator<Optional<FireStation>> iteratorFireStation = fireStationToRemove.listIterator();
		 while( iteratorFireStation.hasNext()) {
			 Optional<FireStation>personItr = iteratorFireStation.next();
			 if (stationNumber.toString().equals(personItr.get().getStationNumber().toString())){
				 iteratorFireStation.remove();
				 
			 }
		 }
		// deleteById(stationNumber);
		}

		void deleteById(String stationNumber);*/
}
