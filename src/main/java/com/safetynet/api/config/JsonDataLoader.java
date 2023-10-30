package com.safetynet.api.config;

import java.io.IOException;
import java.util.ArrayList;
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

	public void loadData() {

	}

	@Override
	public void run(String... args) {

		try {
			List<Person> personsFromFile = this.loadDatasPersonFromFileJson();

			List<FireStation> fireStationFromFile = this.loadDatasFireStationFromFileJson();

			List<MedicalRecord> medicalRecordsFromFile = this.loadDatasMedicalRecordFromFileJson();

			for (Person person : personsFromFile) {
				personService.addPerson(person);
				if (person == null) {
					throw new IOException(" Failed to add person from file");
				}
			}

			for (FireStation fireStation : fireStationFromFile) {
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
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} catch (Exception e) {
			log.error("An error has occured loading datas Json ");
		}
	}

	public List<Person> loadDatasPersonFromFileJson() {
		List<Person> personFromFile = new ArrayList<Person>();
		try {
			personFromFile = personRepository.findAll();
			if (personFromFile.isEmpty()) {
				throw new IOException(" Failed to load datas persons from file");
			} else {
				log.debug("All datas persons load from file");
			}

		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return personFromFile;
	}

	public List<FireStation> loadDatasFireStationFromFileJson() {
		List<FireStation> fireStationFromFile = new ArrayList<FireStation>();
		try {
			fireStationFromFile = fireStationRepository.findAll();
			if (fireStationFromFile.isEmpty()) {
				throw new IOException(" Failed to load datas firestations from file");
			} else {
				log.debug("All datas firestations load from file");
			}

		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return fireStationFromFile;
	}

	public List<MedicalRecord> loadDatasMedicalRecordFromFileJson() {
		List<MedicalRecord> medicalRecordsFromFile = new ArrayList<MedicalRecord>();
		try {
			medicalRecordsFromFile = medicalRecordRepository.findAll();
			if (medicalRecordsFromFile.isEmpty()) {
				throw new IOException(" Failed to load datas  medical records from file");
			} else {
				log.debug("All datas medical records load from file");
			}

		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return medicalRecordsFromFile;
	}
}
