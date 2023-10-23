package com.safetynet.api.service.alertssafetynetservice;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.api.service.dataservice.PersonService;

@Service
public class FloodService {
	private static final Logger log = LogManager.getLogger(FloodService.class);
	@Autowired
	ResidentsOfStationNumberService residentsOfStationNumberService;

	@Autowired
	SearchingInfoOfResidentsByAddressWithMedicalRecordImpl searchingFullInfoOfResidentsWithMedicalRecord;

	public List<Object> getListOfHouseHoldByStationNumber(String stationNumber) throws NullPointerException {
		log.debug("Retrieving  all HouseHold of firestation {}", stationNumber);
		List<Object> listOfHouseHoldOfStationNumber = new ArrayList<Object>();
		try {

			List<Map<String, String>> listOfResidentsOfStationNumber = residentsOfStationNumberService
					.getListOfResidentsOfStationNumber(stationNumber);

			// creating list of address commun between addresses found in each info of
			// resident
			ListIterator<Map<String, String>> itrResidentsOfStationNumber = listOfResidentsOfStationNumber
					.listIterator();
			List<String> listOfAddress = new ArrayList<String>();
			while (itrResidentsOfStationNumber.hasNext()) {
				Map<String, String> itrResidentNext = itrResidentsOfStationNumber.next();

				if (!listOfAddress.contains(itrResidentNext.get("address"))) {
					listOfAddress.add(itrResidentNext.get("address"));
				}			
			}

			// getting medicalrecords searching with address of each resident
			for (String address : listOfAddress) {
				List<Map<String, String>> listOfResidentWithMedicalRecord = new ArrayList<Map<String, String>>();
				listOfResidentWithMedicalRecord = searchingFullInfoOfResidentsWithMedicalRecord
						.searchInfoOfResident(address);	
				listOfHouseHoldOfStationNumber.add(listOfResidentWithMedicalRecord);

			}
			
		} catch (NullPointerException e) {
			log.error("Failed to retrieve   House Hold for firestation: {}", stationNumber);
			throw new NullPointerException(" HouseHold not found at this firestation : " + stationNumber);
		}
		log.info("list Of House Hold found at this firestation {} : {} to prevent for flood ", stationNumber, listOfHouseHoldOfStationNumber );
		return listOfHouseHoldOfStationNumber;
	}
}
