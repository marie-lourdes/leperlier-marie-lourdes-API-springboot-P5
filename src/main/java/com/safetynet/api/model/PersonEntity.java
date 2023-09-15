package com.safetynet.api.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

// initilize attributs of class with constructor and arg
@RequiredArgsConstructor
//create getter with name of attributs
@Accessors(fluent = true) @Getter
@Setter
public class PersonEntity {

	//@NonNull to check or throw NullPointerException
		  private @NonNull String id;
		  private @NonNull String firstName;
		  private @NonNull String lastName;
		  private @NonNull String address;
		  private @NonNull int zip;
		  private @NonNull String city;
		  private @NonNull String phone;
		  private @NonNull String email;

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
