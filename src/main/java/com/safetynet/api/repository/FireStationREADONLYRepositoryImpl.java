package com.safetynet.api.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.safetynet.api.model.FireStation;
import com.safetynet.api.service.ReadFireStationDataFromFileImpl;

@Component
public class FireStationREADONLYRepositoryImpl implements IFireStationREADONLYRepository{
	private Optional<FireStation> fireStationFoundById; 
	private Optional<FireStation>fireStationFoundByAddress;
	private List<Optional<FireStation>> listOfFireStationsFoundById;
	private List<Optional<FireStation>>	listOfFireStationsFoundByAddress;
	@Autowired
	ReadFireStationDataFromFileImpl readFireStations;

	@Override
	public List<FireStation> findAll() throws IOException{	
		return readFireStations.readFile();
	}
	
	@Override
	public List<Optional<FireStation>> findById(String id) {
		 List<FireStation> fireStations = new ArrayList<FireStation>();
		 listOfFireStationsFoundById = new ArrayList<Optional<FireStation>>();
		 fireStationFoundById = Optional.empty();
		 
		try {
			fireStations = readFireStations.readFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		 fireStations.forEach(elem -> {
			String idFireStation = elem.getId();

			if (idFireStation.toString().equals(id.toString())) {
				fireStationFoundById = Optional.ofNullable(elem);
				listOfFireStationsFoundById.add(fireStationFoundById);
			}
		});	
		 System.out.println("listOfFireStationsFoundById  :" + listOfFireStationsFoundById );
		return 	listOfFireStationsFoundById ;
	}
	
	@Override
	public List<Optional<FireStation>>  findByAddress(String address){
		 List<FireStation> fireStations = new ArrayList<FireStation>();		
		 listOfFireStationsFoundByAddress = new ArrayList< Optional<FireStation>>();
		 fireStationFoundByAddress= Optional.empty();
		
		try {
			fireStations = readFireStations.readFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		 fireStations.forEach(elem -> {
			String addressOfFireStation = elem.getAddress();
		
			if (addressOfFireStation .toString().equals(address.toString())) {
				System.out.println("element found by address" + elem);
		
				fireStationFoundByAddress=Optional.ofNullable(elem);
				listOfFireStationsFoundByAddress.add(fireStationFoundByAddress);
			}		 	
	});
		 System.out.println("listOfFireStationsFoundByAddress:" +listOfFireStationsFoundByAddress);
		 return listOfFireStationsFoundByAddress;		
	}		
}
