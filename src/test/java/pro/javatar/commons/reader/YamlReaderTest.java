/*
 * Copyright (c) 2018 Javatar LLC
 * All rights reserved.
 */
package pro.javatar.commons.reader;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Serhii Petrychenko / Javatar LLC
 * @version 19-10-2018
 */

class YamlReaderTest {

    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final int AGE = 3;

    private ResourceReader yamlReader;
    private User expectedUser;

    @BeforeEach
    public void setUp() {
        yamlReader = YamlReader.getInstance();
        expectedUser = new User(NAME, SURNAME, AGE, LocalDateTime.parse("2016-05-28T17:39:44.937"));
    }

    @Test
    void convertToUserObject() {
        User user = yamlReader.getObjectFromFile("yml/user.yml", User.class);
        assertThat(user)
                .isNotNull()
                .isEqualTo(expectedUser);

        InputStream stream = YamlReader.class.getResourceAsStream("YamlReader.yml");
        user = yamlReader.getObjectFromInputStream(stream, User.class);
        assertThat(user)
                .isNotNull()
                .isEqualTo(expectedUser);

        user = yamlReader.getObjectFromResource(YamlReader.class, "YamlReader.yml", User.class);
        assertThat(user)
                .isNotNull()
                .isEqualTo(expectedUser);

        user = yamlReader.getObjectFromResource(YamlReader.class, "YamlReader.yml", new TypeReference<User>() {
        });
        assertThat(user)
                .isNotNull()
                .isEqualTo(expectedUser);
    }

    @Test
    void getUsersList() {
        List<User> users = yamlReader.getListFromFile("yml/users.yml", User.class);
        assertThat(users)
                .isNotNull()
                .hasSize(3);

        InputStream stream = getClass().getResourceAsStream("/yml/users.yml");
        users = yamlReader.getObjectFromInputStream(stream, new TypeReference<List<User>>() {
        });
        assertThat(users)
                .isNotNull()
                .hasSize(3);
    }

    @Test
    void convertToStringAndToObject() {
        String json = yamlReader.getStringFromFile("yml/user.yml");
        assertThat(json).isNotNull();

        User userFromString = yamlReader.getObjectFromString(json, User.class);
        assertThat(userFromString)
                .isNotNull()
                .isEqualTo(expectedUser);
    }

    @Test
    void convertToStringAndToList() {
        String json = yamlReader.getStringFromFile("yml/users.yml");
        assertThat(json).isNotNull();

        List<User> listFromString = yamlReader.getListFromString(json, User.class);
        assertThat(listFromString)
                .isNotNull()
                .hasSize(3);
    }

    @Test
    void notValidJsonProcessException() {
        assertThrows(IllegalArgumentException.class,
                () -> yamlReader.getObjectFromFile("yml/broken-user.json", User.class));
        assertThrows(IllegalArgumentException.class,
                () -> yamlReader.getListFromFile("yml/broken-user.json", User.class));
        assertThrows(ParseContentReaderException.class,
                () -> yamlReader.getObjectFromString("{{{{", User.class));
        assertThrows(ParseContentReaderException.class,
                () -> yamlReader.getListFromString("{{{{", User.class));

        assertThrows(NullPointerException.class,
                () -> yamlReader.getStringFromFile("wrong-source.json"));
    }
}