/*
 * Copyright (c) 2018 Javatar LLC
 * All rights reserved.
 */
package pro.javatar.commons.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * @author Serhii Petrychenko / Javatar LLC
 * @version 19-10-2018
 */

abstract class AbstractReader implements ResourceReader {
    ObjectMapper objectMapper;

    AbstractReader() {
        objectMapper = getObjectMapper();
    }

    AbstractReader(Map<String, Boolean> properties) {
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        for (Map.Entry<String, Boolean> property : properties.entrySet()) {
            objectMapper.configure(SerializationFeature.valueOf(property.getKey()), property.getValue());
        }
    }


    /**
     * @return serialized instance read from file
     */
    @Override
    public <T> T getObjectFromFile(String fileName, Class<T> name) {
        URL resourcePath = getFileFromClasspath(fileName);
        try {
            return objectMapper.readValue(resourcePath, name);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public <T> T getObjectFromResource(String fileName, Class<T> name) {
        InputStream inputStream = name.getResourceAsStream(fileName);
        try {
            return objectMapper.readValue(inputStream, name);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public <T> List<T> getListFromResource(String fileName, Class<T> name) {
        InputStream inputStream = name.getResourceAsStream(fileName);
        try {
            return objectMapper.readValue(inputStream,
                                          objectMapper.getTypeFactory().constructCollectionType(List.class, name));
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * @return String representation of json file
     */
    @Override
    public String getStringFromFile(String fileName) {
        try {
            return IOUtils.toString(getResourceAsStream(fileName), Charset.defaultCharset());
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * @return serialized object read from String
     */
    @Override
    public <T> T getObjectFromString(String json, Class<T> name) {
        try {
            return objectMapper.readValue(json, name);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * @return list of serialized object read from String
     */
    @Override
    public <T> List<T> getListFromString(String json, Class<T> name) {
        try {
            return objectMapper.readValue(json,
                                          objectMapper.getTypeFactory().constructCollectionType(List.class, name));
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public <T> List<T> getListFromFile(String fileName, Class<T> name) {
        URL resourcePath = getFileFromClasspath(fileName);
        try {
            return objectMapper.readValue(resourcePath,
                                          objectMapper.getTypeFactory().constructCollectionType(List.class, name));
        } catch (IOException e) {
            return null;
        }
    }

    private URL getFileFromClasspath(String filename) {
        return JsonReader.class.getClassLoader().getResource(filename);
    }

    private InputStream getResourceAsStream(String resourcePath) {
        return JsonReader.class.getClassLoader().getResourceAsStream(resourcePath);
    }

    protected abstract ObjectMapper getObjectMapper();
}
