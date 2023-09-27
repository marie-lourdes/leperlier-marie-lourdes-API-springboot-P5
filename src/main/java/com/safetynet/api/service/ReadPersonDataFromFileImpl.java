package com.safetynet.api.service;

import java.io.FileReader;

import org.apache.tomcat.util.json.JSONParser;
import org.springframework.boot.configurationprocessor.json.JSONArray;

public class ReadPersonDataFromFileImpl implements IDatasFileReader {
	 private String path = "JSONFile.json"; 
 @Override
public JSONArray readFile() {
	 // Object ob = new JSONParser().parse(new FileReader( path ));
	 return null;
 }

}
