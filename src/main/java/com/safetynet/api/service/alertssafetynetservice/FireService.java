package com.safetynet.api.service.alertssafetynetservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.safetynet.api.model.FireStation;
import com.safetynet.api.service.dataservice.FireStationService;

@Service
public class FireService {
	private static final Logger log = LogManager.getLogger(FireService.class);

	@Autowired
	private FireStationService fireStationService;

	@Autowired
	@Qualifier("InfoOfResidentsByAddressWithMedicalRecord")
	private ISearchingInfoOfResident searchingFullInfoOfResidentsWithMedicalRecord;

	public List<Map<String, String>> getListOfResidentsAndFireStationNearFire(String address)
			throws NullPointerException {
		log.debug("Retrieving all residents with its fireStation near fire address : {}", address);
		List<Map<String, String>> listOfResidentAndFireStationNearFire = new ArrayList<Map<String, String>>();

		try {
			List<Map<String, String>> listOfResidentWithMedicalRecord = new ArrayList<Map<String, String>>();

			listOfResidentWithMedicalRecord = searchingFullInfoOfResidentsWithMedicalRecord
					.searchInfoOfResident(address);
			listOfResidentAndFireStationNearFire = listOfResidentWithMedicalRecord;
			Map<String, String> mapOfFireStationFoundByAddressFire = new HashMap<String, String>();
			List<FireStation> fireStationsFoundByAddressFire = fireStationService.getFireStationsByAddress(address);
			for (FireStation fireStation : fireStationsFoundByAddressFire) {
				mapOfFireStationFoundByAddressFire.put("stationNumber", fireStation.getStationNumber());
			}

			if (!mapOfFireStationFoundByAddressFire.isEmpty()) {
				listOfResidentAndFireStationNearFire.add(mapOfFireStationFoundByAddressFire);
			}

		} catch (Exception e) {
			log.error("Failed to retrieve residents and its firestation near fire address:  {}", address);
			throw new NullPointerException("Residents  and firestation not found near fire address");
		}

		log.debug(" All residents with its fireStation retrieved  near fire address : {}",
				listOfResidentAndFireStationNearFire);
		return listOfResidentAndFireStationNearFire;

	}
}
