package com.safetynet.api.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.safetynet.api.model.FireStation;

public interface IFireStationRepository  extends CrudRepository<FireStation, Long>{
    /*     @Override
      default   public void delete( FireStation fireStation) {
   
       List<String> listOfFireStation = new ArrayList<String>();
         	listOfFireStation.add(fireStation.getStationNumber().toString());
         	listOfFireStation.add(fireStation.getAddress());
        	listOfFireStation.remove(0);
        	
         }*/
	   
		
	
}
