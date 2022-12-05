/*
 * Copyright (c) 2018 Javatar LLC
 * All rights reserved.
 */
package pro.javatar.commons.reader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.cfg.ConfigFeature;
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

    private static final String PARSING_EXCEPTION = "Exception during class '%s' parsing: %s";
    private static final String PARSING_LIST_EXCEPTION = "Exception during list of class '%s' parsing: %s";

    ObjectMapper objectMapper;

    AbstractReader(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    AbstractReader() {
        objectMapper = getObjectMapper();
    }

    AbstractReader(Map<ConfigFeature, Boolean> properties) {
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        for (Map.Entry<ConfigFeature, Boolean> property : properties.entrySet()) {
            ConfigFeature feature = property.getKey();
            if (feature instanceof SerializationFeature) {
                SerializationFeature serializationFeature = SerializationFeature.valueOf(((SerializationFeature) feature).name());
                objectMapper.configure(serializationFeature, property.getValue());
                continue;
            }
            if (feature instanceof DeserializationFeature) {
                DeserializationFeature deserializationFeature = DeserializationFeature.valueOf(((DeserializationFeature) feature).name());
                objectMapper.configure(deserializationFeature, property.getValue());
            }
        }
    }

    /**
     * @return serialized instance read from file
     */
    @Override
    public <T> T getObjectFromFile(String fileName, Class<T> name) {
        try {
            URL resourcePath = getFileFromClasspath(fileName);
            return objectMapper.readValue(resourcePath, name);
        } catch (IOException e) {
            throw new ParseContentReaderException(String.format(PARSING_EXCEPTION, name, e.getMessage()));
        }
    }

    @Override
    public <T> T getObjectFromInputStream(InputStream inputStream, Class<T> name) {
        try {
            return objectMapper.readValue(inputStream, name);
        } catch (IOException e) {
            throw new ParseContentReaderException(String.format(PARSING_EXCEPTION, name, e.getMessage()));
        }
    }

    @Override
    public <T> List<T> getListFromInputStream(InputStream inputStream, Class<T> name) {
        try {
            return objectMapper.readValue(inputStream,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, name));
        } catch (IOException e) {
            throw new ParseContentReaderException(String.format(PARSING_LIST_EXCEPTION, name, e.getMessage()));
        }
    }

    /**
     * @return String representation of json file
     */
    @Override
    public String getStringFromFile(String fileName) {
        try {
            return IOUtils.toString(getResourceAsStream(fileName), Charset.defaultCharset());
        } catch (IOException e) {
            throw new ParseContentReaderException(String.format(PARSING_LIST_EXCEPTION, fileName, e.getMessage()));
        }
    }

    /**
     * @return serialized object read from String
     */
    @Override
    public <T> T getObjectFromString(String json, Class<T> name) {
        try {
            return objectMapper.readValue(json, name);
        } catch (JsonProcessingException e) {
            throw new ParseContentReaderException(String.format(PARSING_EXCEPTION, name, e.getMessage()));
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
        } catch (JsonProcessingException e) {
            throw new ParseContentReaderException(String.format(PARSING_LIST_EXCEPTION, name, e.getMessage()));
        }
    }

    @Override
    public <T> List<T> getListFromFile(String fileName, Class<T> name) {
        try {
            URL resourcePath = getFileFromClasspath(fileName);
            return objectMapper.readValue(resourcePath,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, name));
        } catch (IOException e) {
            throw new ParseContentReaderException(String.format(PARSING_LIST_EXCEPTION, fileName, e.getMessage()));
        }
    }

    @Override
    public <T> T getObjectFromResource(Class resourceClass, String fileName, Class<T> name) {
        InputStream stream = resourceClass.getResourceAsStream(fileName);
        return getObjectFromInputStream(stream, name);
    }

    @Override
    public <T> List<T> getListFromResource(Class resourceClass, String fileName, Class<T> name) {
        InputStream stream = resourceClass.getResourceAsStream(fileName);
        return getListFromInputStream(stream, name);
    }

    @Override
    public <T> T getObjectFromInputStream(InputStream is, TypeReference<T> typeRef) {
        try {
            return objectMapper.readValue(is, typeRef);
        } catch (IOException e) {
            throw new ParseContentReaderException(String.format(PARSING_EXCEPTION, typeRef, e.getMessage()));
        }
    }

    @Override
    public <T> T getObjectFromFile(String fileName, TypeReference<T> typeRef) {
        try {
            URL stream = AbstractReader.class.getResource(fileName);
            return objectMapper.readValue(stream, typeRef);
        } catch (IOException e) {
            throw new ParseContentReaderException(String.format(PARSING_LIST_EXCEPTION, fileName, e.getMessage()));
        }
    }

    @Override
    public <T> T getObjectFromResource(Class resourceClass, String fileName, TypeReference<T> typeRef) {
        InputStream stream = resourceClass.getResourceAsStream(fileName);
        return getObjectFromInputStream(stream, typeRef);
    }

    protected ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    private URL getFileFromClasspath(String filename) {
        return JsonReader.class.getClassLoader().getResource(filename);
    }

    private InputStream getResourceAsStream(String resourcePath) {
        return JsonReader.class.getClassLoader().getResourceAsStream(resourcePath);
    }
}
