package com.safetynet.api.utils;

public class ConstantsRequestResponseHttp {
	//request, response CRUD and routes for data controllers
	public static final String REQUEST_POST_PERSON = "Request POST: '/person', {}";
	public static final String RESPONSE_POST_PERSON = "Response POST: '/person', {}";
	public static final String REQUEST_PUT_PERSON = "Request PUT: '/person', {}";
	public static final String RESPONSE_PUT_PERSON = "Response PUT: '/person', {}";
	public static final String REQUEST_DELETE_PERSON = "Request DELETE: ' /person', {}";
	public static final String RESPONSE_DELETE_PERSON = "Response DELETE: '/person', {}";
	
	public static final String REQUEST_POST_MEDICALRECORD = "Request POST: '/medicalRecord', {}";
	public static final String RESPONSE_POST_MEDICALRECORD  = "Response POST: '/medicalRecord', {}";
	public static final String REQUEST_PUT_MEDICALRECORD  = "Request PUT: '/medicalRecord', {}";
	public static final String RESPONSE_PUT_MEDICALRECORD  = "Response PUT: '/medicalRecord', {}";
	public static final String REQUEST_DELETE_MEDICALRECORD  = "Request DELETE: ' /medicalRecord', {}";
	public static final String RESPONSE_DELETE_MEDICALRECORD = "Response DELETE: '/medicalRecord', {}";
	
	public static final String REQUEST_POST_STATIONNUMBER_OF_FIRESTATION = "Request POST: '/firestation', {}";
	public static final String RESPONSE_POST_STATIONNUMBER_OF_FIRESTATION = "Response POST: '/firestation', {}";
	public static final String REQUEST_POST_ADDRESS_OF_FIRESTATION = "Request POST: '/firestation/{stationNumber}', {}";
	public static final String RESPONSE_POST_ADDRESS_OF_FIRESTATION = "Response POST: '/firestation/{stationNumber}', {}";
	public static final String REQUEST_PUT_FIRESTATION   = "Request PUT: '/firestation', {}";
	public static final String RESPONSE_PUT_FIRESTATION   = "Response PUT: '/firestation', {}";
	public static final String REQUEST_DELETE_STATIONNUMBER_OF_FIRESTATION = "Request DELETE: '/firestation/{stationNumber}', {}";
	public static final String REQUEST_DELETE_ADDRESS_OF_FIRESTATION = "Request DELETE: '/firestation', {}";
	public static final String RESPONSE_DELETE_STATIONNUMBER_OF_FIRESTATION = "Response DELETE: '/firestation', {}";
	public static final String RESPONSE_DELETE_ADDRESS_OF_FIRESTATION = "Response DELETE: '/firestation/{stationNumber}', {}";
	
	//request, response GET and routes for Alertscontroller
	public static final String REQUEST_GET_ADULTSANDCHILDS_OF_STATIONNUMBER = "Request GET: '/firestation', {}";
	public static final String REQUEST_GET_CHILDSANDMEMBERSOFHOUSEHOLD_BY_ADDRESS = "Request GET: '/childAlert', {}";
	public static final String REQUEST_GET_PHONES_OF_RESIDENTS_BY_STATIONNUMBER  = "Request GET: '/phoneAlert', {}";
	public static final String REQUEST_GET_RESIDENTSANDSTATIONNUMBER_NEAR_FIRE = "Request GET: '/fire', {}";
	public static final String REQUEST_GET_HOUSEHOLD_BY_STATIONNUMBER_IF_FLOOD = "Request GET: '/flood/stations', {}";
	public static final String REQUEST_GET_PERSONINFO_BY_FULLNAME = "Request GET: '/personInfo', {}";
	public static final String REQUEST_GET_EMAIL_OF_RESIDENTS_BY_CITY = "Request GET: '/communityEmail', {}";
	
	private  ConstantsRequestResponseHttp () {
		 
	}
}
