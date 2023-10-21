package com.safetynet.api.utils;

public class RegexConstants {
		public static final String REGEX_EMAIL = "^(.+)@(\\S+)[.](\\S+)$";
		public static final String REGEX_PHONE ="\\d{3}[-]?\\d{3}[-]?\\d{4}$";
		public static final String REGEX_ADDRESS = "^\\d(\\S+)\s(\\S+)$";
		//constructor private  for utility class 
		private RegexConstants() {
			
		}	
}
