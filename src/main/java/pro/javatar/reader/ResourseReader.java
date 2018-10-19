/*
 * Copyright (c) 2018 Javatar LLC
 * All rights reserved.
 */

package pro.javatar.reader;

import java.util.List;

/**
 * @author Serhii Petrychenko / Javatar LLC
 * @version 19-10-2018
 */

public interface ResourseReader {
    <T> T getObjectFromFile(String fileName, Class<T> name);

    String getStringFromFile(String fileName);

    <T> T getObjectFromString(String json, Class<T> name);

    <T> List<T> getListFromString(String json, Class<T> name);

    <T> List<T> getListFromFile(String fileName, Class<T> name);
}
