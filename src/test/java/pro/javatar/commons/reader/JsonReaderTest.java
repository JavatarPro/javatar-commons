/*
 * Copyright (c) 2018 Javatar LLC
 * All rights reserved.
 */
package pro.javatar.commons.reader;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

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

    @Test
    public void convertToUserObject() {
        User user = jsonReader.getObjectFromFile("json/user.json", User.class);
        assertNotNull(user);
        assertEquals(expectedUser, user);

        user = jsonReader.getObjectFromResource("JsonReader.json", User.class);
        assertNotNull(user);
        assertEquals(expectedUser, user);
    }

    @Test
    public void getUsersList() {
        List<User> users = jsonReader.getListFromFile("json/users.json", User.class);
        assertNotNull(users);
        assertEquals(3, users.size());
    }

    @Test
    public void convertToStringAndToObject() {
        String json = jsonReader.getStringFromFile("json/user.json");
        assertNotNull(json);

        User userFromString = jsonReader.getObjectFromString(json, User.class);
        assertNotNull(userFromString);
        assertEquals(expectedUser, userFromString);
    }

    @Test
    public void convertToStringAndToList() {
        String json = jsonReader.getStringFromFile("json/users.json");
        assertNotNull(json);

        List<User> listFromString = jsonReader.getListFromString(json, User.class);
        assertNotNull(listFromString);
        assertEquals(3, listFromString.size());
    }

    @Test
    public void notValidJsonProcessException() {
        assertNull(jsonReader.getObjectFromFile("json/broken-user.json", User.class));
        assertNull(jsonReader.getListFromFile("json/broken-user.json", User.class));
        assertNull(jsonReader.getObjectFromString("{{{{", User.class));
        assertNull(jsonReader.getListFromString("{{{{", User.class));

        assertEquals("", jsonReader.getStringFromFile("wrong-source.json"));
    }
}