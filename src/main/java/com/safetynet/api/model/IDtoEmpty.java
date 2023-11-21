package com.safetynet.api.model;

import org.springframework.stereotype.Component;

@Component
public class IDtoEmpty {
	 private final String dataEmpty;
	 public IDtoEmpty(String dataEmpty) {
		    this.dataEmpty = dataEmpty;	  
		  }
	 public String getDataEmpty() {
		    return dataEmpty;
		  }
}

	 
	 