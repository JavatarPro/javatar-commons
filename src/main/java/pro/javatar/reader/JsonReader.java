/*
 * Copyright (c) 2018 Javatar LLC
 * All rights reserved.
 */

package pro.javatar.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * @author Andrii Murashkin / Javatar LLC
 * @version 05-09-2018
 */
public class JsonReader {
    private static final Logger logger = LoggerFactory.getLogger(JsonReader.class);
    private ObjectMapper objectMapper;

    public JsonReader() {
        this.objectMapper = getObjectMapper();
    }

    public <T> T getObjectFromFile(String filename, Class<T> name) {
        URL resourcePath = this.getFileFromClasspath(filename);

        try {
            return this.objectMapper.readValue(resourcePath, name);
        } catch (IOException e) {
            logger.error("Fail to read file {} because of exception {}", filename, e.getMessage());
            return null;
        }
    }

    public String getStringFromFile(String filename) {
        try {
            return IOUtils.toString(this.getResourceAsStream(filename));
        } catch (IOException e) {
            logger.error("Fail to read file {} because of exception {}", filename, e.getMessage());
            return null;
        }
    }

    public <T> T getObjectFromString(String json, Class<T> name) {
        try {
            return this.objectMapper.readValue(json, name);
        } catch (IOException e) {
            logger.error("Fail to create object {} from json {}", name, json);
            return null;
        }
    }

    public <T> List<T> getListFromString(String json, Class<T> name) {
        try {
            return (List) this.objectMapper.readValue(json, this.objectMapper.getTypeFactory().constructCollectionType(List.class, name));
        } catch (IOException e) {
            logger.error("Fail to create list from json {}", json);
            return null;
        }
    }

    public <T> List<T> getListFromFile(String filename, Class<T> name) {
        URL resourcePath = this.getFileFromClasspath(filename);

        try {
            return (List) this.objectMapper.readValue(resourcePath, this.objectMapper.getTypeFactory().constructCollectionType(List.class, name));
        } catch (IOException e) {
            logger.error("Fail to create object from filename {}", filename);
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
