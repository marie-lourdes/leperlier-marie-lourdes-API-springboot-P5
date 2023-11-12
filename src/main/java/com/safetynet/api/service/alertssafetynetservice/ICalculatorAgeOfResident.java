package com.safetynet.api.service.alertssafetynetservice;

import java.math.BigInteger;

public interface ICalculatorAgeOfResident {
	BigInteger calculateAgeOfResident(String idFirstAndLastName) throws Exception;
}
