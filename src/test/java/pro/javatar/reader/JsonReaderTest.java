/*
 * Copyright (c) 2018 Javatar LLC
 * All rights reserved.
 */

package pro.javatar.reader;

import static org.junit.Assert.*;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Andrii Murashkin / Javatar LLC
 * @version 06-09-2018
 */
public class JsonReaderTest {

    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final int AGE = 3;
    private static final String USER_NAME_REPRESENTATION = "\"name\": \"name\"";
    private static final String USER_SURNAME_REPRESENTATION = "\"surname\": \"surname\"";
    private static final String USER_AGE_REPRESENTATION = "\"age\": \"4\"";

    private JsonReader jsonReader = new JsonReader();

    @Test
    public void convertToUserObject() {
        User user = jsonReader.getObjectFromFile("user.json", User.class);
        assertNotNull(user);
        assertEquals(getTestUser(), user);
    }

    @Test
    public void getUsersList(){
        List<User> users = jsonReader.getListFromFile("users.json", User.class);
        assertNotNull(users);
        assertEquals(3, users.size());
    }
    @Test
    public void convertToUserString() {
        String user = jsonReader.getStringFromFile("user.json");
        assertNotNull(user);
        assertTrue(user.contains(USER_NAME_REPRESENTATION));
        assertTrue(user.contains(USER_SURNAME_REPRESENTATION));
        assertFalse(user.contains(USER_AGE_REPRESENTATION));
    }


    private User getTestUser() {
        return new User(NAME, SURNAME, AGE, LocalDateTime.parse("2016-05-28T17:39:44.937"));
    }
}