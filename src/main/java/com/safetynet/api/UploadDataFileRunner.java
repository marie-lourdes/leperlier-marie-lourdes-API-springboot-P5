package com.safetynet.api;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.safetynet.api.model.PersonEntity;
@Component
public class UploadDataFileRunner implements CommandLineRunner {
	public PersonEntity person;
	@Override
	public void run(String... args) throws Exception {
		
		System.out.println(person);
		
	}

}
