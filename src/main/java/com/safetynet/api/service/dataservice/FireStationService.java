package com.safetynet.api.service.dataservice;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.safetynet.api.model.FireStation;
import com.safetynet.api.repository.IFireStationRepository;

public class FireStationService {
	@Autowired
    private IFireStationRepository fireStationRepository;

    public List<FireStation> getAllFireStations() {
        return (List<FireStation>) fireStationRepository.findAll();
    }

    public  Optional<FireStation> getOneFireStationById(Long id) {
        return  fireStationRepository.findById(id);
    }
    
    public FireStation saveOneFireStation(FireStation fireStation) {
       return fireStationRepository.save(fireStation);       
    }
    
    public  FireStation updateOneFireStationById(FireStation fireStation, Long id) {	   
        return fireStationRepository.save(fireStation);       
     }
}
