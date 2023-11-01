package com.safetynet.api.config;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.Person;
import com.safetynet.api.repository.FireStationRepositoryImpl;
import com.safetynet.api.repository.MedicalRecordRepositoryImpl;
import com.safetynet.api.repository.PersonRepositoryImpl;
import com.safetynet.api.service.dataservice.FireStationService;
import com.safetynet.api.service.dataservice.MedicalRecordService;
import com.safetynet.api.service.dataservice.PersonService;

@Component
public class JsonDataLoader implements CommandLineRunner {
	private static final Logger log = LogManager.getLogger(JsonDataLoader.class);
	private final PersonService personService;
	private final MedicalRecordService medicalRecordService;
	private final FireStationService fireStationService;
	private final PersonRepositoryImpl personRepository;
	private final FireStationRepositoryImpl fireStationRepository;
	private final MedicalRecordRepositoryImpl medicalRecordRepository;

	public JsonDataLoader(PersonService personService, FireStationService fireStationService,
			MedicalRecordService medicalRecordService, PersonRepositoryImpl personRepository,
			FireStationRepositoryImpl fireStationRepository, MedicalRecordRepositoryImpl medicalRecordRepository) {
		this.personService = personService;
		this.fireStationService = fireStationService;
		this.medicalRecordService = medicalRecordService;
		this.personRepository = personRepository;
		this.fireStationRepository = fireStationRepository;
		this.medicalRecordRepository = medicalRecordRepository;
	}

	@Override
	public void run(String... args) {
		try {
			List<Person> personsFromFile = this.loadDatasPersonFromFileJson();
			List<FireStation> fireStationsFromFile = this.loadDatasFireStationFromFileJson();
			List<MedicalRecord> medicalRecordsFromFile = this.loadDatasMedicalRecordFromFileJson();

			addAllDatasFromFileJson(personsFromFile, fireStationsFromFile, medicalRecordsFromFile);

		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("An error has occured loading datas Json ");
		}
	}

	public List<Person> loadDatasPersonFromFileJson() throws IOException {
		List<Person> personsFromFile = personRepository.findAll();

		if (personsFromFile.isEmpty()) {
			throw new IOException(" Failed to load datas persons from file");
		} else {
			log.debug("All datas persons load from file");
		}

		return personsFromFile;
	}

	public List<FireStation> loadDatasFireStationFromFileJson() throws IOException {
		List<FireStation> fireStationsFromFile = fireStationRepository.findAll();

		if (fireStationsFromFile.isEmpty()) {
			throw new IOException(" Failed to load datas firestations from file");
		} else {
			log.debug("All datas firestations load from file");
		}

		return fireStationsFromFile;
	}

	public List<MedicalRecord> loadDatasMedicalRecordFromFileJson() throws IOException {
		List<MedicalRecord> medicalRecordsFromFile = medicalRecordRepository.findAll();

		if (medicalRecordsFromFile.isEmpty()) {
			throw new IOException(" Failed to load datas  medical records from file");
		} else {
			log.debug("All datas medical records load from file");
		}

		return medicalRecordsFromFile;
	}

	public void addAllDatasFromFileJson(List<Person> personsFromFile, List<FireStation> fireStationsFromFile,
			List<MedicalRecord> medicalRecordsFromFile) throws IOException {

		for (Person person : personsFromFile) {
			personService.addPerson(person);
			if (person == null) {
				throw new IOException(" Failed to add person from file");
			}
		}

		for (FireStation fireStation : fireStationsFromFile) {
			fireStationService.addFireStation(fireStation);
			if (fireStation == null) {
				throw new IOException(" Failed to add firestation  from file");
			}
		}

		for (MedicalRecord medicalRecord : medicalRecordsFromFile) {
			medicalRecordService.addMedicalRecord(medicalRecord);
			if (medicalRecord == null) {
				throw new IOException(" Failed to add medical record from file");
			}
		}
	}
}
