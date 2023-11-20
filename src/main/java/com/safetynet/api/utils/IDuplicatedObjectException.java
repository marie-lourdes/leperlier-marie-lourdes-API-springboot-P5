package com.safetynet.api.utils;

import java.util.List;

public interface IDuplicatedObjectException<T> {
 void isObjectDuplicatedById(List<T> datas, T data) throws IllegalArgumentException;
}
