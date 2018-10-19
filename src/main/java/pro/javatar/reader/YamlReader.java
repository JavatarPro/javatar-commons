/*
 * Copyright (c) 2018 Javatar LLC
 * All rights reserved.
 */

package pro.javatar.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

/**
 * @author Serhii Petrychenko / Javatar LLC
 * @version 19-10-2018
 */

public class YamlReader extends AbstractReader implements ResourseReader {

    public static ResourseReader getInstance(){
        return new YamlReader();
    }

    @Override
    protected ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        objectMapper.findAndRegisterModules();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return objectMapper;
    }
}
