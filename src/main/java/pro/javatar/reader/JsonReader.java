/*
 * Copyright (c) 2018 Javatar LLC
 * All rights reserved.
 */

package pro.javatar.reader;

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
 * @author Andrii Murashkin / Javatar LLC
 * @version 05-09-2018
 */
public class JsonReader {
    private ObjectMapper objectMapper;

    public JsonReader() {
        objectMapper = getObjectMapper();
    }

    public JsonReader(Map<String, Boolean> properties) {
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        for (Map.Entry<String, Boolean> property : properties.entrySet()) {
            objectMapper.configure(SerializationFeature.valueOf(property.getKey()), property.getValue());
        }
    }

    /**
     * @return serialized instance read from file
     */
    public <T> T getObjectFromFile(String fileName, Class<T> name) {
        URL resourcePath = getFileFromClasspath(fileName);
        try {
            return objectMapper.readValue(resourcePath, name);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * @return String representation of json file
     */
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
    public <T> List<T> getListFromString(String json, Class<T> name) {
        try {
            return objectMapper.readValue(json,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, name));
        } catch (IOException e) {
            return null;
        }
    }

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

    private ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return objectMapper;
    }
}
