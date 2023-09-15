package com.safetynet.api.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@RequiredArgsConstructor
//create getter with name of attributs
@Accessors(fluent = true) @Getter
@Setter
public class PersonEntity {
	
		  private String id;
		  private String firstName;
		  private String lastName;
		  private String address;
		  private int zip;
		  private String city;
		  private String phone;
		  private String email;

		  @Override
		  public String toString() {
		      return "Person{" +
		              "id=" + id +
		              ", first name='" + firstName + '\'' +
		              ", last name=" + lastName +
		              ", address=" + address +
		              ", zip=" + zip +
		              ", city=" + city +
		              ", phone=" + phone +
		              ", email=" + email +
		              '}';
		  }
}
