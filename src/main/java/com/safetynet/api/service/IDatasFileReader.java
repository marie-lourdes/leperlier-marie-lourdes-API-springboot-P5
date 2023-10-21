package com.safetynet.api.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import com.safetynet.api.utils.DataSourceConstants;

public interface IDatasFileReader<T> {
	List<T> readFile() throws IOException;

	default JsonArray readDataJson(String dataNameJson) throws IOException {	
		InputStream is = new FileInputStream( DataSourceConstants.PATH);
		JsonReader jsonReader = Json.createReader(is);
		JsonObject datasJsonObject = jsonReader.readObject();
		JsonArray jsonArray = datasJsonObject.getJsonArray(dataNameJson);
		System.out.println("all datas Json " + dataNameJson + " read:" + jsonArray);
		jsonReader.close();
		is.close();
		return jsonArray;

	}
}
