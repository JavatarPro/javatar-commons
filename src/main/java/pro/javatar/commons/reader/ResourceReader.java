/*
 * Copyright (c) 2018 Javatar LLC
 * All rights reserved.
 */
package pro.javatar.commons.reader;

import com.fasterxml.jackson.core.type.TypeReference;

import java.io.InputStream;
import java.util.List;

/**
 * @author Serhii Petrychenko / Javatar LLC
 * @version 19-10-2018
 */

public interface ResourceReader {

    <T> T getObjectFromFile(String fileName, Class<T> name);

    <T> T getObjectFromFile(String fileName, TypeReference<T> typeRef);

    <T> T getObjectFromInputStream(InputStream is, Class<T> name);

    <T> T getObjectFromInputStream(InputStream is, TypeReference<T> typeRef);

    <T> T getObjectFromResource(Class resourceClass, String fileName, Class<T> name);

    <T> T getObjectFromResource(Class resourceClass, String fileName, TypeReference<T> typeRef);

    String getStringFromFile(String fileName);

    <T> T getObjectFromString(String json, Class<T> name);

    <T> List<T> getListFromString(String json, Class<T> name);

    <T> List<T> getListFromFile(String fileName, Class<T> name);

    <T> List<T> getListFromInputStream(InputStream is, Class<T> name);

    <T> List<T> getListFromResource(Class resourceClass, String fileName, Class<T> name);

}
