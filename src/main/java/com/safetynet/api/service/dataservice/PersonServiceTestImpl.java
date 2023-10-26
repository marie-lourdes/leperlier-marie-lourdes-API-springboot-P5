package com.safetynet.api.service.dataservice;

import com.safetynet.api.model.Person;

public class PersonServiceTestImpl  implements IPersonServiceTest {
	public Person getOnePersonById(String id) {
		return new Person (id,"John","Boyd","1509 Culver St","Culver","97451","841-874-6512","jaboyd@email.com");
	}
}
