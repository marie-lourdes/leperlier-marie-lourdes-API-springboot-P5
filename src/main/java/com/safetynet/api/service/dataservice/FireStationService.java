package com.safetynet.api.service.dataservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.api.model.FireStation;
import com.safetynet.api.repository.IFireStationRepository;

@Service
public class FireStationService {
	@Autowired
    private IFireStationRepository fireStationRepository;

    public List<FireStation> getAllFireStations() {
        return (List<FireStation>) fireStationRepository.findAll();
    }

    public  Optional<FireStation> getOneFireStationById(Long id) {
        return  fireStationRepository.findById(id);
    }
    
    public  FireStation saveFireStation(FireStation fireStation) {
       return fireStationRepository.save(fireStation);       
    }
    
    public  FireStation updateOneFireStationById(FireStation fireStation, Long id) {	   
        return fireStationRepository.save(fireStation);       
     }
 
    public void deleteStationNumberFireStation(FireStation fireStation, Long id){ 
    	fireStationRepository.deleteById(id);
    	/*List<String> listOfFireStation = new ArrayList<String>();
    	listOfFireStation.add(fireStation.getStationNumber().toString());
    	listOfFireStation.add(fireStation.getAddress());
   	listOfFireStation.remove(0);
   	return listOfFireStation;
        //fireStationRepository.deleteById(id);*/
     }
}
