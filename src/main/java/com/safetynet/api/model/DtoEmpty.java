package com.safetynet.api.model;

import org.springframework.stereotype.Component;

@Component
public class DtoEmpty {
	 private final String dataEmpty;
	 public DtoEmpty(String dataEmpty) {
		    this.dataEmpty = dataEmpty;	  
		  }
	 public String getDataEmpty() {
		    return dataEmpty;
		  }
}

	 
	 