package com.safetynet.api.service;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.apache.tomcat.util.json.JSONParser;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;



public class ReadPersonDataFromFileImpl implements IDatasFileReader {
	 private String path = "src/main/resources/datasSafetyNetAlerts.json"; 
 @SuppressWarnings("rawtypes")
@Override
public void readFile() throws FileNotFoundException {
	Object ob = new JSONParser(new FileReader( path ));

	JSONObject js =  (JSONObject) ob;
	
	 JSONArray personData = null;
	try {
		   personData = js.getJSONArray("phoneNumbers");
		
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

     System.out.println("list Person is: " + personData);
 }

}
