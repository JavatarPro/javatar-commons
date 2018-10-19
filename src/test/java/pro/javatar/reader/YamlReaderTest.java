/*
 * Copyright (c) 2018 Javatar LLC
 * All rights reserved.
 */

package pro.javatar.reader;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Serhii Petrychenko / Javatar LLC
 * @version 19-10-2018
 */

public class YamlReaderTest {

    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final int AGE = 3;

    private ResourseReader yamlReader;
    private User expectedUser;

    @Before
    public void setUp() {
        yamlReader = YamlReader.getInstance();
        expectedUser = new User(NAME, SURNAME, AGE, LocalDateTime.parse("2016-05-28T17:39:44.937"));
    }

    @Test
    public void convertToUserObject() {
        User user = yamlReader.getObjectFromFile("yml/user.yml", User.class);
        assertNotNull(user);
        assertEquals(expectedUser, user);
    }

    @Test
    public void getUsersList() {
        List<User> users = yamlReader.getListFromFile("yml/users.yml", User.class);
        assertNotNull(users);
        assertEquals(3, users.size());
    }

    @Test
    public void convertToStringAndToObject() {
        String json = yamlReader.getStringFromFile("yml/user.yml");
        assertNotNull(json);

        User userFromString = yamlReader.getObjectFromString(json, User.class);
        assertNotNull(userFromString);
        assertEquals(expectedUser, userFromString);
    }

    @Test
    public void convertToStringAndToList() {
        String json = yamlReader.getStringFromFile("yml/users.yml");
        assertNotNull(json);

        List<User> listFromString = yamlReader.getListFromString(json, User.class);
        assertNotNull(listFromString);
        assertEquals(3, listFromString.size());
    }

    @Test
    public void notValidJsonProcessException() {
        assertNull(yamlReader.getObjectFromFile("yml/broken-user.yml", User.class));
        assertNull(yamlReader.getListFromFile("yml/broken-user.yml", User.class));
        assertNull(yamlReader.getObjectFromString("{{{{", User.class));
        assertNull(yamlReader.getListFromString("{{{{", User.class));

        assertEquals("", yamlReader.getStringFromFile("yml/wrong-source.yml"));
    }
}