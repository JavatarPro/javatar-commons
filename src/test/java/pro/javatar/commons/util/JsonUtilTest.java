package pro.javatar.commons.util;

import org.junit.Test;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static pro.javatar.commons.util.JsonUtil.REPLACEMENT;
import static pro.javatar.commons.util.JsonUtil.sanitize;

public class JsonUtilTest {

    @Test
    public void sanitizeObject() {

        TestObject testObject = new TestObject("Javatar", 2, Arrays.asList("security", "CI/CD"), Collections.singletonMap("Ukrainian", "C1"));

        String sanitizedString = sanitize(testObject);
        assertThat(sanitizedString, is("{\"name\":\"" + REPLACEMENT + "\",\"age\":\"" + REPLACEMENT + "\",\"skills\":[\"" + REPLACEMENT + "\",\"" + REPLACEMENT + "\"],\"langs\":{\"Ukrainian\":\"" + REPLACEMENT + "\"}}"));
    }

    @Test
    public void sanitizeStringObject() {
        String sanitizedString = sanitize("{\"name\":\"Javatar\",\"age\":2}");
        assertThat(sanitizedString, is("{\"name\":\"" + REPLACEMENT + "\",\"age\":\"" + REPLACEMENT + "\"}"));
    }

    @Test
    public void sanitizeStringObjectWithCustomReplacement() {
        String sanitizedString = sanitize("{\"name\":\"Javatar\",\"age\":2}", "---");
        assertThat(sanitizedString, is("{\"name\":\"---\",\"age\":\"---\"}"));
    }

    @Test
    public void sanitizeStringList() {
        String sanitizedString = sanitize("[1,2]");
        assertThat(sanitizedString, is("[\"" + REPLACEMENT + "\",\"" + REPLACEMENT + "\"]"));
    }

    @Test
    public void sanitizeString() {
        String sanitizedString = "Just a string";
        assertThat(sanitize(sanitizedString), is(REPLACEMENT));
    }

    @Test
    public void sanitizeWithExcludedFields() {
        TestObject testObject = new TestObject("Javatar", 2, Arrays.asList("security", "CI/CD"), Collections.singletonMap("Ukrainian", "C1"));

        Set<String> excludedFields = new HashSet<>(Arrays.asList("age", "langs", "skills"));
        String sanitizedString = sanitize(testObject, excludedFields);
        assertThat(sanitizedString, is("{\"name\":\"" + REPLACEMENT + "\",\"age\":2,\"skills\":[\"security\",\"CI/CD\"],\"langs\":{\"Ukrainian\":\"C1\"}}"));
    }

    @Test
    public void sanitizeStringObjectWithExcludedFields() {
        String sanitizedString = sanitize("{\"name\":\"Javatar\",\"age\":2}", Collections.singleton("age"));
        assertThat(sanitizedString, is("{\"name\":\"" + REPLACEMENT + "\",\"age\":2}"));
    }

    @Test
    public void sanitizeStringObjectWithExcludedFieldsAndCustomReplacement() {
        String sanitizedString = sanitize("{\"name\":\"Javatar\",\"age\":2}", Collections.singleton("age"), "##");
        assertThat(sanitizedString, is("{\"name\":\"##\",\"age\":2}"));
    }

    @Test
    public void objectIsNull() {
        assertThat(sanitize(null), nullValue());
    }

    private static class TestObject {
        private String name;
        private Integer age;
        private List<String> skills;
        private Map<String, String> langs;

        public TestObject(String name, Integer age, List<String> skills, Map<String, String> langs) {
            this.name = name;
            this.age = age;
            this.skills = skills;
            this.langs = langs;
        }

        public String getName() {
            return name;
        }

        public Integer getAge() {
            return age;
        }

        public List<String> getSkills() {
            return skills;
        }

        public Map<String, String> getLangs() {
            return langs;
        }
    }
}
