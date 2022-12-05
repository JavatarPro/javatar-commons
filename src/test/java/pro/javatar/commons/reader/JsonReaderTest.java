/*
 * Copyright (c) 2018 Javatar LLC
 * All rights reserved.
 */
package pro.javatar.commons.reader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.cfg.ConfigFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Andrii Murashkin / Javatar LLC
 * @version 06-09-2018
 */
class JsonReaderTest {

    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final int AGE = 3;

    private ResourceReader jsonReader;
    private User expectedUser;

    @BeforeEach
    void setUp() {
        jsonReader = JsonReader.getInstance();
        expectedUser = new User(NAME, SURNAME, AGE, LocalDateTime.parse("2016-05-28T17:39:44.937"));
    }

    @Test
    void constructorWithProperties() {
        Map<ConfigFeature, Boolean> config = new HashMap<>(3);
        config.put(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, Boolean.TRUE);
        config.put(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, Boolean.FALSE);

        var stream = getClass().getResourceAsStream("JsonReader.json");
        assertThrows(ParseContentReaderException.class,
                () -> JsonReader.getInstance(config).getObjectFromInputStream(stream, User.class));
    }

    @Test
    void convertToUserObject() {
        var user = jsonReader.getObjectFromFile("json/user.json", User.class);
        assertThat(user)
                .isNotNull()
                .isEqualTo(expectedUser);

        var stream = JsonReader.class.getResourceAsStream("JsonReader.json");
        user = jsonReader.getObjectFromInputStream(stream, User.class);
        assertThat(user)
                .isNotNull()
                .isEqualTo(expectedUser);

        user = jsonReader.getObjectFromResource(JsonReader.class, "JsonReader.json", User.class);
        assertThat(user)
                .isNotNull()
                .isEqualTo(expectedUser);

        user = jsonReader.getObjectFromResource(JsonReader.class, "JsonReader.json", new TypeReference<User>() {
        });
        assertThat(user)
                .isNotNull()
                .isEqualTo(expectedUser);
    }

    @Test
    void getUsersList() throws IOException {
        List<User> users = jsonReader.getListFromFile("json/users.json", User.class);
        assertThat(users)
                .isNotNull()
                .hasSize(3);

        InputStream stream = getClass().getResourceAsStream("/json/users.json");
        users = jsonReader.getObjectFromInputStream(stream, new TypeReference<List<User>>() {
        });
        assertThat(users)
                .isNotNull()
                .hasSize(3);

        users = jsonReader.getObjectFromFile("/json/users.json", new TypeReference<List<User>>() {
        });
        assertThat(users)
                .isNotNull()
                .hasSize(3);
    }

    @Test
    void convertToStringAndToObject() throws IOException {
        String json = jsonReader.getStringFromFile("json/user.json");
        assertThat(json).isNotNull();

        User userFromString = jsonReader.getObjectFromString(json, User.class);
        assertThat(userFromString)
                .isNotNull()
                .isEqualTo(expectedUser);
    }

    @Test
    void convertToStringAndToList() throws IOException {
        String json = jsonReader.getStringFromFile("json/users.json");
        assertThat(json).isNotNull();

        List<User> listFromString = jsonReader.getListFromString(json, User.class);
        assertThat(listFromString)
                .isNotNull()
                .hasSize(3);
    }

    @Test
    void notValidJsonProcessException() {
        assertThrows(ParseContentReaderException.class,
                () -> jsonReader.getObjectFromFile("json/broken-user.json", User.class));
        assertThrows(ParseContentReaderException.class,
                () -> jsonReader.getListFromFile("json/broken-user.json", User.class));
        assertThrows(ParseContentReaderException.class,
                () -> jsonReader.getObjectFromString("{{{{", User.class));
        assertThrows(ParseContentReaderException.class,
                () -> jsonReader.getListFromString("{{{{", User.class));

        assertThrows(NullPointerException.class,
                () -> jsonReader.getStringFromFile("wrong-source.json"));
    }
}