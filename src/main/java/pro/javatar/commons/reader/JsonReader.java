/*
 * Copyright (c) 2018 Javatar LLC
 * All rights reserved.
 */
package pro.javatar.commons.reader;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.cfg.ConfigFeature;

import java.util.Map;

/**
 * @author Andrii Murashkin / Javatar LLC
 * @version 05-09-2018
 */
public class JsonReader extends AbstractReader {

    public static ResourceReader getInstance(){
        return new JsonReader();
    }

    public static ResourceReader getInstance(Map<ConfigFeature, Boolean> configs){
        return new JsonReader(configs);
    }

    private JsonReader() {
    }

    private JsonReader(Map<ConfigFeature, Boolean> properties) {
        super(properties);
    }

    @Override
    protected ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }
}
