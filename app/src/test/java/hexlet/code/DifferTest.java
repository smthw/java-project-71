package hexlet.code;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class DifferTest {

    @Test
    void testGenerate() throws Exception {
        String expected =
                """
                {
                  - follow: false
                    host: hexlet.io
                  - proxy: 123.234.53.22
                  - timeout: 50
                  + timeout: 20
                  + verbose: true
                }""";

        String actual = Differ.generate("src/test/resources/f1.json", "src/test/resources/f2.json");

        assertEquals(expected, actual);
    }

    @Test
    void testFirstFileNotFound() {
        assertThrows(
                Exception.class,
                () ->
                        Differ.generate(
                                "src/test/resources/nonexistent.json",
                                "src/test/resources/f2.json"));
    }

    @Test
    void testSecondFileNotFound() {
        assertThrows(
                Exception.class,
                () ->
                        Differ.generate(
                                "src/test/resources/f1.json",
                                "src/test/resources/nonexistent.json"));
    }

    @Test
    void testGenerateYaml() throws Exception {
        String expected =
                """
                {
                  - follow: false
                    host: hexlet.io
                  - proxy: 123.234.53.22
                  - timeout: 50
                  + timeout: 20
                  + verbose: true
                }""";

        String actual = Differ.generate("src/test/resources/f1.yml", "src/test/resources/f2.yml");

        assertEquals(expected, actual);
    }

    @Test
    void testGenerateIdenticalFiles() throws Exception {
        String expected = """
            {
                follow: false
                host: hexlet.io
                proxy: 123.234.53.22
                timeout: 50
            }""";

        String actual = Differ.generate(
                "src/test/resources/f1.json",
                "src/test/resources/f1.json"
        );

        assertEquals(expected, actual);
    }

    @Test
    void testGenerateNested() throws Exception {
        String expected = """
            {
                chars1: [a, b, c]
              - chars2: [d, e, f]
              + chars2: false
              - checked: false
              + checked: true
              - default: null
              + default: [value1, value2]
              - id: 45
              + id: null
              - key1: value1
              + key2: value2
                numbers1: [1, 2, 3, 4]
              - numbers2: [2, 3, 4, 5]
              + numbers2: [22, 33, 44, 55]
              - numbers3: [3, 4, 5]
              + numbers4: [4, 5, 6]
              + obj1: {nestedKey=value, isNested=true}
              - setting1: Some value
              + setting1: Another value
              - setting2: 200
              + setting2: 300
              - setting3: true
              + setting3: none
            }""";

        String actual = Differ.generate(
                "src/test/resources/f3.json",
                "src/test/resources/f4.json"
        );

        assertEquals(expected, actual);
    }
}
