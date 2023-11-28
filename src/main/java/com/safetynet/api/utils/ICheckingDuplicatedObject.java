package com.safetynet.api.utils;

import java.util.List;

public interface ICheckingDuplicatedObject<T> {
	boolean isObjectDuplicated(List<T> datas, T data) throws  Exception;
}
