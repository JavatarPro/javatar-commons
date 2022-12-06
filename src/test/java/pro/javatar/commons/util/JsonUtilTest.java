package pro.javatar.commons.util;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static pro.javatar.commons.util.JsonUtil.REPLACEMENT;
import static pro.javatar.commons.util.JsonUtil.sanitize;

class JsonUtilTest {

    @Test
    void sanitizeObject() {

        TestObject testObject = new TestObject("Javatar", 2, Arrays.asList("security", "CI/CD"), Collections.singletonMap("Ukrainian", "C1"));

        String sanitizedString = sanitize(testObject);
        assertThat(sanitizedString)
                .isEqualTo("{\"name\":\"" + REPLACEMENT + "\",\"age\":\"" + REPLACEMENT + "\",\"skills\":[\"" + REPLACEMENT + "\",\"" + REPLACEMENT + "\"],\"langs\":{\"Ukrainian\":\"" + REPLACEMENT + "\"}}");
    }

    @Test
    void sanitizeStringObject() {
        String sanitizedString = sanitize("{\"name\":\"Javatar\",\"age\":2}");
        assertThat(sanitizedString)
                .isEqualTo("{\"name\":\"" + REPLACEMENT + "\",\"age\":\"" + REPLACEMENT + "\"}");
    }

    @Test
    void sanitizeStringObjectWithCustomReplacement() {
        String sanitizedString = sanitize("{\"name\":\"Javatar\",\"age\":2}", "---");
        assertThat(sanitizedString)
                .isEqualTo("{\"name\":\"---\",\"age\":\"---\"}");
    }

    @Test
    void sanitizeStringList() {
        String sanitizedString = sanitize("[1,2]");
        assertThat(sanitizedString)
                .isEqualTo("[\"" + REPLACEMENT + "\",\"" + REPLACEMENT + "\"]");
    }

    @Test
    void sanitizeString() {
        String sanitizedString = "Just a string";
        assertThat(sanitize(sanitizedString)).isEqualTo(REPLACEMENT);
    }

    @Test
    void sanitizeWithExcludedFields() {
        TestObject testObject = new TestObject("Javatar", 2, Arrays.asList("security", "CI/CD"), Collections.singletonMap("Ukrainian", "C1"));

        Set<String> excludedFields = new HashSet<>(Arrays.asList("age", "langs", "skills"));
        String sanitizedString = sanitize(testObject, excludedFields);
        assertThat(sanitizedString)
                .isEqualTo("{\"name\":\"" + REPLACEMENT + "\",\"age\":2,\"skills\":[\"security\",\"CI/CD\"],\"langs\":{\"Ukrainian\":\"C1\"}}");
    }

    @Test
    void sanitizeStringObjectWithExcludedFields() {
        String sanitizedString = sanitize("{\"name\":\"Javatar\",\"age\":2}", Collections.singleton("age"));
        assertThat(sanitizedString).isEqualTo("{\"name\":\"" + REPLACEMENT + "\",\"age\":2}");
    }

    @Test
    void sanitizeStringObjectWithExcludedFieldsAndCustomReplacement() {
        String sanitizedString = sanitize("{\"name\":\"Javatar\",\"age\":2}", Collections.singleton("age"), "##");
        assertThat(sanitizedString).isEqualTo("{\"name\":\"##\",\"age\":2}");
    }

    @Test
    void objectIsNull() {
        assertThat(sanitize(null)).isNull();
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
