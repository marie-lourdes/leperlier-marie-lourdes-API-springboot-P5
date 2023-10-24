package com.safetynet.api.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.safetynet.api.utils.DataSourceConstants;

public interface IDatasFileReader<T> {
	static final Logger log = LogManager.getLogger(IDatasFileReader.class);

	List<T> readFile() throws IOException;

	default JsonArray readDataJson(String dataNameJson) throws IOException {
		log.debug("Reading datas {} from file json", dataNameJson);
		JsonArray jsonArray = null;
		try {
			InputStream is = new FileInputStream(DataSourceConstants.PATH);
			JsonReader jsonReader = Json.createReader(is);
			JsonObject datasJsonObject = jsonReader.readObject();
			jsonArray = datasJsonObject.getJsonArray(dataNameJson);
			System.out.println("all datas Json " + dataNameJson + " read:" + jsonArray);
			jsonReader.close();
			is.close();

		} catch (Exception e) {
			e.getStackTrace();
			log.error("An error has occured in reading datas {} from file json", dataNameJson);
		}
		log.debug("Datas {} successfully read from file json", dataNameJson);
		return jsonArray;
	}
}
