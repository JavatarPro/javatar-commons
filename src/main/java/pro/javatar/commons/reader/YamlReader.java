/*
 * Copyright (c) 2018 Javatar LLC
 * All rights reserved.
 */
package pro.javatar.commons.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.cfg.ConfigFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.util.Map;

/**
 * @author Serhii Petrychenko / Javatar LLC
 * @version 19-10-2018
 */

public class YamlReader extends AbstractReader implements ResourceReader {

    public static ResourceReader getInstance(){
        return new YamlReader();
    }

    public static ResourceReader getInstance(Map<ConfigFeature, Boolean> config){
        return new YamlReader(config);
    }

    private YamlReader() {
    }

    private YamlReader(Map<ConfigFeature, Boolean> properties) {
        super(properties);
    }

    @Override
    protected ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        objectMapper.findAndRegisterModules();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return objectMapper;
    }
}
