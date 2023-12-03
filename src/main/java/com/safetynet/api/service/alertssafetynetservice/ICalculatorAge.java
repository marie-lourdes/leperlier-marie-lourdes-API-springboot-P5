package com.safetynet.api.service.alertssafetynetservice;

import java.math.BigInteger;

public interface ICalculatorAge {
	BigInteger calculateAgeOfResident(String idFirstAndLastName) throws Exception;
}
