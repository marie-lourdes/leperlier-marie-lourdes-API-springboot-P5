package com.safetynet.api.service.alertssafetynetservice;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FloodService {
	@Autowired
	ResidentsOfStationNumberService residentsOfStationNumberService;
	
	@Autowired
	SearchingFullInfoOfResidentsByAddressImpl searchingFullInfoOfResidentsByAddress ;
	
	List< String> listOfAddress = new ArrayList <String>();
	
	public List<Map<String, String>> getListOfHouseHoldByStationNumber(String stationNumber) {
		List<Map<String, String>> listOfResidentsOfStationNumber =residentsOfStationNumberService. getListOfResidentsOfStationNumber(stationNumber);
		List<Object> listOfHouseHoldOfStationNumber = new ArrayList<Object>();
	 
		System.out.println("listOfResidentsOfStationNumber of flood service" + listOfResidentsOfStationNumber);
		ListIterator<Map<String,String>> itrResidentsOfStationNumber = listOfResidentsOfStationNumber.listIterator();
		
		while(itrResidentsOfStationNumber.hasNext()) {
			Map<String, String>	itrResidentNextToNext = itrResidentsOfStationNumber.next();
		
				if(!listOfAddress.contains(itrResidentNextToNext.get("address"))) {	
					listOfAddress.add(itrResidentNextToNext.get("address"));
					//itrResidentNextToNext =	itrResidentsOfStationNumber.next();
				}
				
				
		}
		for(String address:listOfAddress ) {
			List<Map<String, String>> listOfHouseHoldSameaddress = searchingFullInfoOfResidentsByAddress.searchInfoOfResident(address);
			 listOfHouseHoldOfStationNumber.add(listOfHouseHoldSameaddress);
		}	
		System.out.println(" listOfHouseHoldOfStationNumber"+ listOfHouseHoldOfStationNumber);
		System.out.println("listOfAddress"+listOfAddress);
		
		return listOfResidentsOfStationNumber ;
	}
}
