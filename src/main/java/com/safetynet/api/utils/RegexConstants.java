package com.safetynet.api.utils;

public class RegexConstants {
	public static final String REGEX_EMAIL = "^(.+)@(\\S+)[.](\\S+)$";
	public static final String REGEX_PHONE = "\\d{3}[-]?\\d{3}[-]?\\d{4}$";
	public static final String REGEX_ADDRESS = "^\\d(.+)\s(\\S+)$";
	public static final String REGEX_DATE = "^[0-3][0-9]/[0-3][0-9]/(?:[0-9][0-9])?[0-9][0-9]$";

	// constructor private for utility class
	private RegexConstants() {

	}
}
