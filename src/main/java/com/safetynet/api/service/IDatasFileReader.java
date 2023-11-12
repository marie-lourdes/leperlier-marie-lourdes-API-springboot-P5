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

		JsonArray jsonArray = null;
		InputStream is =null;
		JsonReader jsonReader=null;
		try {
			if (DataSourceConstants.PATH != null) {
				is = new FileInputStream(DataSourceConstants.PATH);

				log.debug("Reading datas {} from file json", dataNameJson);
				jsonReader = Json.createReader(is);

				log.debug("Parsing data Json  {}", dataNameJson);
				JsonObject datasJsonObject = jsonReader.readObject();
				jsonArray = datasJsonObject.getJsonArray(dataNameJson);

				log.debug("Datas {} read and parsed from file json", dataNameJson);
				log.debug("All datas Json  {}  from file json : {}", dataNameJson, jsonArray);
			}

		} catch (Exception e) {
			e.getStackTrace();
			log.error("An error has occured in reading datas {} from file json", dataNameJson);
		}finally {
			is.close();
			jsonReader.close();
		}
		log.debug("Datas {} successfully read from file json", dataNameJson);
		return jsonArray;
	}
}
