/*
 * Copyright (c) 2018 Javatar LLC
 * All rights reserved.
 */
package pro.javatar.commons.reader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.cfg.ConfigFeature;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * @author Andrii Murashkin / Javatar LLC
 * @version 06-09-2018
 */
public class JsonReaderTest {

    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final int AGE = 3;

    private ResourceReader jsonReader;
    private User expectedUser;

    @Before
    public void setUp() {
        jsonReader = JsonReader.getInstance();
        expectedUser = new User(NAME, SURNAME, AGE, LocalDateTime.parse("2016-05-28T17:39:44.937"));
    }

    @Test(expected = IOException.class)
    public void constructorWithProperties() throws IOException {
        Map<ConfigFeature, Boolean> config = new HashMap<>(3);
        config.put(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, Boolean.TRUE);
        config.put(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, Boolean.FALSE);

        InputStream stream = getClass().getResourceAsStream("JsonReader.json");
        JsonReader.getInstance(config).getObjectFromInputStream(stream, User.class);
    }

    @Test
    public void convertToUserObject() throws IOException {
        User user = jsonReader.getObjectFromFile("json/user.json", User.class);
        assertNotNull(user);
        assertEquals(expectedUser, user);

        InputStream stream = JsonReader.class.getResourceAsStream("JsonReader.json");
        user = jsonReader.getObjectFromInputStream(stream, User.class);
        assertNotNull(user);
        assertEquals(expectedUser, user);

        user = jsonReader.getObjectFromResource(JsonReader.class,"JsonReader.json", User.class);
        assertNotNull(user);
        assertEquals(expectedUser, user);

        user = jsonReader.getObjectFromResource(JsonReader.class, "JsonReader.json", new TypeReference<User>() {});
        assertNotNull(user);
        assertEquals(expectedUser, user);
    }

    @Test
    public void getUsersList() throws IOException {
        List<User> users = jsonReader.getListFromFile("json/users.json", User.class);
        assertNotNull(users);
        assertEquals(3, users.size());

        InputStream stream = getClass().getResourceAsStream("/json/users.json");
        users = jsonReader.getObjectFromInputStream(stream, new TypeReference<List<User>>() {});
        assertNotNull(users);
        assertEquals(3, users.size());

        users = jsonReader.getObjectFromFile("/json/users.json", new TypeReference<List<User>>() {});
        assertNotNull(users);
        assertEquals(3, users.size());
    }

    @Test
    public void convertToStringAndToObject() throws IOException {
        String json = jsonReader.getStringFromFile("json/user.json");
        assertNotNull(json);

        User userFromString = jsonReader.getObjectFromString(json, User.class);
        assertNotNull(userFromString);
        assertEquals(expectedUser, userFromString);
    }

    @Test
    public void convertToStringAndToList() throws IOException {
        String json = jsonReader.getStringFromFile("json/users.json");
        assertNotNull(json);

        List<User> listFromString = jsonReader.getListFromString(json, User.class);
        assertNotNull(listFromString);
        assertEquals(3, listFromString.size());
    }

    @Test(expected = IOException.class)
    public void notValidJsonProcessException() throws IOException {
        assertNull(jsonReader.getObjectFromFile("json/broken-user.json", User.class));
        assertNull(jsonReader.getListFromFile("json/broken-user.json", User.class));
        assertNull(jsonReader.getObjectFromString("{{{{", User.class));
        assertNull(jsonReader.getListFromString("{{{{", User.class));

        assertEquals("", jsonReader.getStringFromFile("wrong-source.json"));
    }
}