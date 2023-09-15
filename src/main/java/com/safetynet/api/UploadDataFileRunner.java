package com.safetynet.api;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.safetynet.api.model.PersonEntity;
@Component
public class UploadDataFileRunner implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		PersonEntity person = new PersonEntity( );
		System.out.println(person.getClass());
		
	}

}
