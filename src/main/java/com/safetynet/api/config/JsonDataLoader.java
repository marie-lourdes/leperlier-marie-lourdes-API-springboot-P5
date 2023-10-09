package com.safetynet.api.config;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.repository.MedicalRecordRepositoryImpl;
import com.safetynet.api.service.ReadMedicalRecordDataFromFileImpl;
import com.safetynet.api.service.dataservice.MedicalRecordService;



@Component

public class JsonDataLoader implements CommandLineRunner {



   // private final PersonService personService;

  //  private final FireStationService fireStationService;

    private final MedicalRecordService medicalRecordService;
   
    private final MedicalRecordRepositoryImpl readMedicalRecordsFromfile;
    @Autowired
    public JsonDataLoader(

          //  PersonService personService,

          //  FireStationService fireStationService,

           MedicalRecordService medicalRecordService,
           MedicalRecordRepositoryImpl readMedicalRecordsFromfile

    ) {

       // this.personService = personService;

       // this.fireStationService = fireStationService;
    	this.medicalRecordService = medicalRecordService;
        this.readMedicalRecordsFromfile = readMedicalRecordsFromfile;

    }

    @Override
    public void run(String... args) throws IOException {
    	
       try{

    	   List<MedicalRecord> medicalRecords=	readMedicalRecordsFromfile.findAll();
    	   for(MedicalRecord medicalRecord:medicalRecords) {
    		   medicalRecordService.addMedicalRecord(medicalRecord);
    		
    	   }

        } catch (IOException e) {

            e.printStackTrace();

        }

   }

}
