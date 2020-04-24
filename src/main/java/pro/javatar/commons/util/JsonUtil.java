/*
 * Copyright (c) 2020 Javatar LLC
 * All rights reserved.
 */

package pro.javatar.commons.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Serhii Petrychenko / Javatar LLC
 * @version 23-04-2020
 */

public final class JsonUtil {
    static final String REPLACEMENT = "***";
    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    private JsonUtil() {
    }

    public static String sanitize(Object object) {
        return sanitize(object, Collections.emptySet(), REPLACEMENT);
    }

    public static String sanitize(Object object, String replacement) {
        return sanitize(object, Collections.emptySet(), replacement);
    }

    public static String sanitize(Object object, Set<String> excludedFields, String replacement) {
        if (object == null) {
            return null;
        }
        String json;
        if (object instanceof String) {
            json = (String) object;
        } else {
            try {
                json = objectMapper.writeValueAsString(object);
            } catch (JsonProcessingException e) {
                return replacement;
            }
        }
        return sanitizeStringObject(json, excludedFields, replacement);
    }

    public static String sanitize(Object object, Set<String> excludedFields) {
        return sanitize(object, excludedFields, REPLACEMENT);
    }


    private static String sanitizeStringObject(String body, Set<String> excludedFields, String replacement) {
        try {
            Object responseMap = objectMapper.readValue(body, Object.class);
            sanitizeObject(responseMap, excludedFields, replacement);
            return objectMapper.writeValueAsString(responseMap);
        } catch (IOException e) {
            return replacement;
        }
    }

    private static Map<String, Object> sanitizeMap(Map<String, Object> responseMap, Set<String> excludedFields, String replacement) {
        for (Map.Entry<String, Object> entry : responseMap.entrySet()) {
            if (!excludedFields.contains(entry.getKey())) {
                responseMap.replace(entry.getKey(), sanitizeObject(entry.getValue(), excludedFields, replacement));
            }
        }
        return responseMap;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static List sanitizeList(List list, Set<String> excludedFields, String replacement) {
        for (int i = 0; i < list.size(); i++) {
            list.set(i, sanitizeObject(list.get(i), excludedFields, replacement));
        }
        return list;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static Object sanitizeObject(Object item, Set<String> excludedFields, String replacement) {
        if (item instanceof Map) {
            return sanitizeMap((Map<String, Object>) item, excludedFields, replacement);
        }
        if (item instanceof List) {
            return sanitizeList((List) item, excludedFields, replacement);
        }
        return replacement;
    }
}
