package com.safetynet.api.config;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.Person;
import com.safetynet.api.repository.MedicalRecordRepositoryImpl;
import com.safetynet.api.repository.PersonRepositoryImpl;
import com.safetynet.api.service.dataservice.MedicalRecordService;
import com.safetynet.api.service.dataservice.PersonService;

@Component
public class JsonDataLoader implements CommandLineRunner {

    private final PersonService personService;

  //  private final FireStationService fireStationService;

    private final MedicalRecordService medicalRecordService;
   
    private final MedicalRecordRepositoryImpl medicalRecordRepository;
    private final PersonRepositoryImpl personRepository;
    @Autowired
    public JsonDataLoader(
           PersonService personService,
           MedicalRecordService medicalRecordService,
           //  FireStationService fireStationService,
           PersonRepositoryImpl personRepository,    
           MedicalRecordRepositoryImpl medicalRecordRepository

    ) {

        this.personService = personService;

       // this.fireStationService = fireStationService;
    	this.medicalRecordService = medicalRecordService;
        this.medicalRecordRepository = medicalRecordRepository;
        this.personRepository= personRepository;
    }

    @Override
    public void run(String... args) {
    	
       try{

    	   List<MedicalRecord> medicalRecordsFromFile=	medicalRecordRepository.findAll();
    	   for(MedicalRecord medicalRecord:medicalRecordsFromFile) {
    		   medicalRecordService.addMedicalRecord(medicalRecord);	
    	   }

    	   List<Person> personsFromFile=	personRepository.findAll();
    	   for(Person person:personsFromFile) {
    		   personService.addPerson(person);	
    	   }
        } catch (IOException e) {

            e.printStackTrace();

        }
   }

}
