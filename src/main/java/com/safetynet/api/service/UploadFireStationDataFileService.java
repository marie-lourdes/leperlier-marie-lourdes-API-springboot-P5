package com.safetynet.api.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.safetynet.api.model.FireStation;

public class UploadFireStationDataFileService {

	@Autowired
	ReadFireStationDataFromFileImpl readFireStations;

	public List<FireStation> getFireStationsFromFile() throws IOException {
		return readFireStations.readFile();
	}
}
