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
}
