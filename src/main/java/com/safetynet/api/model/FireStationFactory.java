package com.safetynet.api.model;


public class FireStationFactory {
	public enum FireStationType {
		STATIONNUMBER_REMOVED, ADDRESS_REMOVED,
	};

	public static FireStation makeFireStation(FireStationType  type) {
		switch (type) {
		case STATIONNUMBER_REMOVED:
			return new FireStationWithAddressOnly();
		case ADDRESS_REMOVED :
			return new FireStationWithStationNumberOnly();
		}

		return new FireStation();
	}


}
